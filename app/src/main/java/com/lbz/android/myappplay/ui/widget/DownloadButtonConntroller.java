package com.lbz.android.myappplay.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppDownloadInfo;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.DownloadFlag;
import com.lbz.android.myappplay.bean.event.AppDetailPageDownLoadBtnClickEvent;
import com.lbz.android.myappplay.bean.event.AppInstallEvent;
import com.lbz.android.myappplay.bean.event.DownloadFinishEvent;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.commom.util.AppUtils;
import com.lbz.android.myappplay.commom.util.PermissionUtil;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.di.component.DaggerDownloadComponent;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadRecord;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.lbz.android.myappplay.commom.Constant.BASE_DOWNLOAD_URL;

/**
 * Created by elitemc on 2017/9/21.
 */

public class DownloadButtonConntroller {

    @Inject
    RxDownload mRxDownload;

    @Inject
    ApiService mApiService;

    public DownloadButtonConntroller(MyApplication myApplication) {

//        Log.e("1111", "myApplication=" + ((myApplication != null) ? "not null"+myApplication : "null"));
        if (myApplication != null) {
//            Log.e("1111", "myApplication getAppComponent=" + ((myApplication.getAppComponent() != null) ? "not null" : "null"));
            DaggerDownloadComponent.builder().appComponent(myApplication.getAppComponent()).build().inject(this);
        }

//        Log.e("1111", "myApplication getAppComponent=" + ((myApplication.getAppComponent() != null) ? "not null" : "null"));
    }

    /**
     * downloadlist初始化下载按钮
     *
     * @param btn
     * @param record
     */
    public void handClick(final DownloadProgressButton btn, DownloadRecord record) {

//        Log.e("1111","在下载列表 handClick="+record.getUrl());

        AppInfo info = downloadRecord2AppInfo(record);

        receiveDownloadStatus(record.getUrl()).subscribe(new DownloadConsumer(btn, info));

    }

    /**
     * downloadlist初始化下载按钮
     *
     * @param helper
     * @param record
     */
    public void handClick(BaseViewHolder helper, DownloadRecord record) {

        AppInfo info = downloadRecord2AppInfo(record);

        receiveDownloadStatus(record.getUrl()).subscribe(new DownloadManagerConsumer(helper, info));

    }

    public Observable<?> deleteServiceDownload(DownloadRecord record) {
        if (mRxDownload != null) {
            return mRxDownload.deleteServiceDownload(record.getUrl(), true);
        } else {
            return null;
        }
    }


    /**
     * applist初始化下载按钮
     *
     * @param btn
     * @param appInfo
     */
    public void handClick(final DownloadProgressButton btn, final AppInfo appInfo) {
//        Log.e("1111", "mRxDownload =" + ((mRxDownload != null) ? "not null" + mRxDownload : "null")+"----"+appInfo.getDisplayName());
//        Log.e("1111", "mApiService =" + ((mApiService != null) ? "not null" + mApiService : "null")+"----"+appInfo.getDisplayName());

        if (mApiService == null || mRxDownload == null) {
            return;
        }

        isAppInstalled(btn.getContext(), appInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        if (DownloadFlag.UN_INSTALL == event.getFlag()) {

                            return isApkFileExsit(btn.getContext(), appInfo);

                        }
                        if (DownloadFlag.INSTALLED == event.getFlag()) {

                            return isShouldUpdate(btn.getContext(), appInfo);

                        }

                        return Observable.just(event);
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        if (DownloadFlag.FILE_EXIST == event.getFlag()) {

                            return getAppDownloadInfo(appInfo)

                                    .flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                        @Override
                                        public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                            appInfo.setAppDownloadInfo(appDownloadInfo);

                                            return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
                                        }
                                    });

                        }


                        return Observable.just(event);
                    }
                })
                .compose(RxHttpResponseCompose.composeSchedulers())

                .subscribe(new DownloadConsumer(btn, appInfo));

        RxBus.getDefault().toObservable(AppInstallEvent.class)
                .filter(new Predicate<AppInstallEvent>() {
                    @Override
                    public boolean test(@NonNull AppInstallEvent appInstallEvent) throws Exception {
                        return appInstallEvent.getPgName().equals(appInfo.getPackageName());
                    }
                })
                .flatMap(new Function<AppInstallEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull AppInstallEvent appInstallEvent) throws Exception {
                        DownloadEvent event = new DownloadEvent();
                        String flag = appInstallEvent.getFLAG();

                        if (flag.equals(Intent.ACTION_PACKAGE_ADDED) || flag.equals(Intent.ACTION_PACKAGE_REPLACED)) {

                            event.setFlag(DownloadFlag.INSTALLED);

                        }
                        return Observable.just(event);
                    }
                }).subscribe(new DownloadConsumer(btn, appInfo));


    }


    public void handClick2(final DownloadButton btn, final AppInfo appInfo,int position) {
//        Log.e("1111", "mRxDownload =" + ((mRxDownload != null) ? "not null" + mRxDownload : "null")+"----"+appInfo.getDisplayName());
//        Log.e("1111", "mApiService =" + ((mApiService != null) ? "not null" + mApiService : "null")+"----"+appInfo.getDisplayName());

        if (mApiService == null || mRxDownload == null) {
            return;
        }

        isAppInstalled(btn.getContext(), appInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        if (DownloadFlag.UN_INSTALL == event.getFlag()) {

                            return isApkFileExsit(btn.getContext(), appInfo);

                        }
                        if (DownloadFlag.INSTALLED == event.getFlag()) {

                            return isShouldUpdate(btn.getContext(), appInfo);

                        }

                        return Observable.just(event);
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        if (DownloadFlag.FILE_EXIST == event.getFlag()) {

                            return getAppDownloadInfo(appInfo)

                                    .flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                        @Override
                                        public ObservableSource<DownloadEvent> apply(@NonNull AppDownloadInfo appDownloadInfo) throws Exception {

                                            appInfo.setAppDownloadInfo(appDownloadInfo);

                                            return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
                                        }
                                    });

                        }


                        return Observable.just(event);
                    }
                })
                .compose(RxHttpResponseCompose.composeSchedulers())

                .subscribe(new AppDetailDownloadConsumer(btn, appInfo,position));

        RxBus.getDefault().toObservable(AppInstallEvent.class)
                .filter(new Predicate<AppInstallEvent>() {
                    @Override
                    public boolean test(@NonNull AppInstallEvent appInstallEvent) throws Exception {
                        return appInstallEvent.getPgName().equals(appInfo.getPackageName());
                    }
                })
                .flatMap(new Function<AppInstallEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull AppInstallEvent appInstallEvent) throws Exception {
                        DownloadEvent event = new DownloadEvent();
                        String flag = appInstallEvent.getFLAG();

                        if (flag.equals(Intent.ACTION_PACKAGE_ADDED) || flag.equals(Intent.ACTION_PACKAGE_REPLACED)) {

                            event.setFlag(DownloadFlag.INSTALLED);

                        }
                        return Observable.just(event);
                    }
                }).subscribe(new AppDetailDownloadConsumer(btn, appInfo,position));


    }

    /**
     * 点击下载按钮处理
     *
     * @param btn
     * @param appInfo
     */
    private void bindClick(final DownloadProgressButton btn, final AppInfo appInfo) {

        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                switch (flag) {

                    case DownloadFlag.INSTALLED:

                        runApp(btn.getContext(), appInfo.getPackageName());

                        break;

                    case DownloadFlag.STARTED:

                        pausedDownload(appInfo.getAppDownloadInfo().getDownloadUrl());

                        break;

                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:
                    case DownloadFlag.SHOULD_UPDATE:

                        startDownload(btn, appInfo);

                        break;

                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), appInfo);

                        break;

                }
            }
        });

    }

    private void bindClick2(final DownloadButton btn, final AppInfo appInfo , final int position) {

        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                
                switch (flag) {

                    case DownloadFlag.INSTALLED:

                        runApp(btn.getContext(), appInfo.getPackageName());

                        RxBus.getDefault().post(new AppDetailPageDownLoadBtnClickEvent(appInfo,position));

                        break;

                    case DownloadFlag.STARTED:

                        pausedDownload(appInfo.getAppDownloadInfo().getDownloadUrl());

                        RxBus.getDefault().post(new AppDetailPageDownLoadBtnClickEvent(appInfo,position));

                        break;

                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:
                    case DownloadFlag.SHOULD_UPDATE:

                        startDownload2(btn, appInfo,position);

                        break;

                    case DownloadFlag.COMPLETED:

                        installApp(btn.getContext(), appInfo);

                        RxBus.getDefault().post(new AppDetailPageDownLoadBtnClickEvent(appInfo,position));

                        break;

                }
            }
        });

    }

    /**
     * 判断是否安装
     *
     * @param context
     * @param appInfo
     * @return
     */
    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo) {

        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context, appInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);

        return Observable.just(event);

    }

    /**
     * 判断apk文件是否存在
     *
     * @param context
     * @param appInfo
     * @return
     */
    public Observable<DownloadEvent> isApkFileExsit(Context context, AppInfo appInfo) {

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + "/" + appInfo.getReleaseKeyHash() + ".apk";

        File file = new File(path);

        DownloadEvent event = new DownloadEvent();

        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);

        return Observable.just(event);

    }

    /**
     * 判断app是否需要更新
     *
     * @param context
     * @param appInfo
     * @return
     */
    private ObservableSource<DownloadEvent> isShouldUpdate(Context context, AppInfo appInfo) {
        DownloadEvent event = new DownloadEvent();
        event.setFlag(AppUtils.isShouldUpdate(context, appInfo) ? DownloadFlag.SHOULD_UPDATE : DownloadFlag.INSTALLED);
        return Observable.just(event);
    }

    /**
     * 根据id获取应用下载信息
     *
     * @param appInfo
     * @return
     */
    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {

        return mApiService.getAppDownloadInfo(appInfo.getId()).compose(RxHttpResponseCompose.composeSchedulers());

    }

    public Observable<DownloadEvent> receiveDownloadStatus(String url) {

        return mRxDownload.receiveDownloadStatus(url);

    }

    /**
     * 运行app
     *
     * @param context
     * @param packageName
     */
    private void runApp(Context context, String packageName) {

        AppUtils.runApp(context, packageName);

    }

    /**
     * 暂停下载
     *
     * @param url
     */
    private void pausedDownload(String url) {

        mRxDownload.pauseServiceDownload(url).subscribe();

    }

    /**
     * 安装app
     *
     * @param context
     * @param appInfo
     */
    private void installApp(Context context, AppInfo appInfo) {

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + "/" + appInfo.getReleaseKeyHash() + ".apk";

        AppUtils.installApk(context, path);

    }

    /**
     * AppInfo转DownloadBean
     *
     * @param info
     * @return
     */
    private DownloadBean appInfo2DownloadBean(AppInfo info) {

        DownloadBean downloadBean = new DownloadBean();

        downloadBean.setUrl(info.getAppDownloadInfo().getDownloadUrl());
        downloadBean.setSaveName(info.getReleaseKeyHash() + ".apk");


        downloadBean.setExtra1(info.getId() + "");
        downloadBean.setExtra2(info.getIcon());
        downloadBean.setExtra3(info.getDisplayName());
        downloadBean.setExtra4(info.getPackageName());
        downloadBean.setExtra5(info.getReleaseKeyHash());

        return downloadBean;
    }

    /**
     * downloadRecord转DownloadBean
     *
     * @param bean
     * @return
     */
    public AppInfo downloadRecord2AppInfo(DownloadRecord bean) {
        AppInfo info = new AppInfo();

        info.setId(Integer.parseInt(bean.getExtra1()));
        info.setIcon(bean.getExtra2());
        info.setDisplayName(bean.getExtra3());
        info.setPackageName(bean.getExtra4());
        info.setReleaseKeyHash(bean.getExtra5());

        AppDownloadInfo downloadInfo = new AppDownloadInfo();
        downloadInfo.setApk(bean.getUrl().replace(BASE_DOWNLOAD_URL, ""));

        info.setAppDownloadInfo(downloadInfo);

        return info;

    }

    /**
     * 下载
     *
     * @param btn
     * @param info
     */
    private void download(DownloadProgressButton btn, AppInfo info) {


        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();

        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl()).subscribe(new DownloadConsumer(btn, info));

    }

    private void download2(DownloadButton btn, AppInfo info ,int position) {


        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();

        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl()).subscribe(new AppDetailDownloadConsumer(btn, info,position));

        RxBus.getDefault().post(new AppDetailPageDownLoadBtnClickEvent(info,position));

    }

    private void startDownload(final DownloadProgressButton btn, final AppInfo appInfo) {

        PermissionUtil.requestPermisson(btn.getContext(), WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                        if (aBoolean) {
                            final AppDownloadInfo downloadInfo = appInfo.getAppDownloadInfo();
                            if (downloadInfo == null) {
                                getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                                    @Override
                                    public void accept(AppDownloadInfo appDownloadInfo) throws Exception {

                                        appInfo.setAppDownloadInfo(appDownloadInfo);

                                        download(btn, appInfo);
                                    }
                                });

                            } else {
                                download(btn, appInfo);
                            }
                        }
                    }
                });

    }

    private void startDownload2(final DownloadButton btn, final AppInfo appInfo,final int position) {

        PermissionUtil.requestPermisson(btn.getContext(), WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                        if (aBoolean) {
                            final AppDownloadInfo downloadInfo = appInfo.getAppDownloadInfo();
                            if (downloadInfo == null) {
                                getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                                    @Override
                                    public void accept(AppDownloadInfo appDownloadInfo) throws Exception {

                                        appInfo.setAppDownloadInfo(appDownloadInfo);

                                        download2(btn, appInfo,position);
                                    }
                                });

                            } else {
                                download2(btn, appInfo,position);
                            }
                        }
                    }
                });

    }


    class DownloadConsumer implements Consumer<DownloadEvent> {

        DownloadProgressButton btn;

        AppInfo mAppInfo;

        public DownloadConsumer(DownloadProgressButton b, AppInfo appInfo) {

            this.btn = b;

            this.mAppInfo = appInfo;

        }

        @Override
        public void accept(@NonNull DownloadEvent event) throws Exception {

            int flag = event.getFlag();

            btn.setTag(R.id.tag_apk_flag, flag);

            bindClick(btn, mAppInfo);

            switch (flag) {

                case DownloadFlag.INSTALLED:
                    btn.setText("运行");
                    break;

                case DownloadFlag.NORMAL:
                    btn.download();
                    break;

                case DownloadFlag.STARTED:
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    break;

                case DownloadFlag.PAUSED:
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    btn.paused();
                    break;

                case DownloadFlag.COMPLETED: //已完成
                    btn.setText("安装");
                    //installApp(btn.getContext(),mAppInfo);
                    break;
                case DownloadFlag.FAILED://下载失败
                    btn.setText("失败");
                    break;
                case DownloadFlag.DELETED: //已删除

                    break;
                case DownloadFlag.SHOULD_UPDATE://可升级
                    btn.setText("升级");
                    break;

            }

        }
    }

    class AppDetailDownloadConsumer implements Consumer<DownloadEvent> {

        DownloadButton btn;

        AppInfo mAppInfo;

        int position;

        public AppDetailDownloadConsumer(DownloadButton b, AppInfo appInfo ,int position) {

            this.btn = b;

            this.mAppInfo = appInfo;

            this.position = position;

        }

        @Override
        public void accept(@NonNull DownloadEvent event) throws Exception {

            int flag = event.getFlag();

            btn.setTag(R.id.tag_apk_flag, flag);

            bindClick2(btn, mAppInfo,position);

            switch (flag) {

                case DownloadFlag.INSTALLED:
                    btn.setCurrentText("运行");
                    break;

                case DownloadFlag.NORMAL:
                    btn.setCurrentText("下载");
                    btn.setState(DownloadButton.STATE_DOWNLOADING);

                    break;

                case DownloadFlag.STARTED:
                    btn.setState(DownloadButton.STATE_DOWNLOADING);
                    btn.setCurrentText("下载中"+(((int)btn.getProgress()))+"%");
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    break;

                case DownloadFlag.PAUSED:
                    btn.setCurrentText("继续");
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    btn.setState(DownloadButton.STATE_PAUSE);
                    break;

                case DownloadFlag.COMPLETED: //已完成
                    btn.setCurrentText("安装");
                    //installApp(btn.getContext(),mAppInfo);
                    break;
                case DownloadFlag.FAILED://下载失败
                    btn.setCurrentText("失败");
                    break;
                case DownloadFlag.DELETED: //已删除

                    break;
                case DownloadFlag.SHOULD_UPDATE://可升级
                    btn.setCurrentText("升级");
                    break;

            }

        }
    }

    class DownloadManagerConsumer implements Consumer<DownloadEvent> {

        BaseViewHolder helper;

        AppInfo mAppInfo;

        public DownloadManagerConsumer(BaseViewHolder helper, AppInfo appInfo) {

            this.helper = helper;

            this.mAppInfo = appInfo;

        }

        @Override
        public void accept(@NonNull DownloadEvent event) throws Exception {

            int flag = event.getFlag();

            DownloadProgressButton btn = helper.getView(R.id.btn_download);

            TextView mPercent = helper.getView(R.id.percent);

            ProgressBar mProgress = helper.getView(R.id.progress);

            TextView mStatus = helper.getView(R.id.status);

            TextView mSize = helper.getView(R.id.size);


            btn.setTag(R.id.tag_apk_flag, flag);

            bindClick(btn, mAppInfo);

            switch (flag) {

                case DownloadFlag.INSTALLED:
                    btn.setText("运行");
                    break;

                case DownloadFlag.NORMAL:
                    btn.download();
                    break;

                case DownloadFlag.STARTED:
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    mStatus.setText("下载中...");
                    break;

                case DownloadFlag.PAUSED:
                    btn.setProgress((int) event.getDownloadStatus().getPercentNumber());
                    btn.paused();
                    mStatus.setText("已暂停");
                    break;

                case DownloadFlag.COMPLETED: //已完成
                    btn.setText("安装");
                    mStatus.setText("下载已完成");
                    RxBus.getDefault().post(new DownloadFinishEvent(helper, mAppInfo));
                    //installApp(btn.getContext(),mAppInfo);
                    break;
                case DownloadFlag.WAITING: //等待中
                    mStatus.setText("等待中");
                    break;
                case DownloadFlag.FAILED://下载失败
                    btn.setText("失败");
                    mStatus.setText("下载失败");
                    break;
                case DownloadFlag.DELETED: //已删除

                    break;

            }

            updateProgressStatus(mProgress, mPercent, mSize, event.getDownloadStatus());

        }

        public void updateProgressStatus(ProgressBar mProgress, TextView mPercent, TextView mSize, DownloadStatus status) {
            mProgress.setIndeterminate(status.isChunked);
            mProgress.setMax((int) status.getTotalSize());
            mProgress.setProgress((int) status.getDownloadSize());
            mPercent.setText(status.getPercent());
            mSize.setText(status.getFormatStatusString());
        }
    }

}

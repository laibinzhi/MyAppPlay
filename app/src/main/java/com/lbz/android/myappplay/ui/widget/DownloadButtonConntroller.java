package com.lbz.android.myappplay.ui.widget;

import android.content.Context;
import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppDownloadInfo;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.DownloadFlag;
import com.lbz.android.myappplay.commom.Constant;
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
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
     * 初始化下载按钮
     * @param btn
     * @param appInfo
     */
    public  void  handClick(final DownloadProgressButton btn, final AppInfo appInfo){
//        Log.e("1111", "mRxDownload =" + ((mRxDownload != null) ? "not null" + mRxDownload : "null")+"----"+appInfo.getDisplayName());
//        Log.e("1111", "mApiService =" + ((mApiService != null) ? "not null" + mApiService : "null")+"----"+appInfo.getDisplayName());

        if (mApiService==null||mRxDownload==null){
            return;
        }

        isAppInstalled(btn.getContext(),appInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        if(DownloadFlag.UN_INSTALL == event.getFlag()){

                            return  isApkFileExsit(btn.getContext(),appInfo);

                        }

                        return  Observable.just(event);
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadEvent event) throws Exception {

                        if(DownloadFlag.FILE_EXIST == event.getFlag()){

                            return  getAppDownloadInfo(appInfo)

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

                .subscribe(new DownloadConsumer(btn,appInfo));

    }

    /**
     * 点击下载按钮处理
     * @param btn
     * @param appInfo
     */
    private void bindClick(final DownloadProgressButton btn, final AppInfo appInfo) {

        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                Log.e("testdown","flag="+flag);

                switch (flag){

                    case DownloadFlag.INSTALLED:

                        runApp(btn.getContext(),appInfo.getPackageName());

                        break;

                    case DownloadFlag.STARTED:

                        pausedDownload(appInfo.getAppDownloadInfo().getDownloadUrl());

                        break;

                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:

                        startDownload(btn,appInfo);

                        break;

                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(),appInfo);

                        break;

                }
            }
        });

    }

    /**
     * 判断是否安装
     * @param context
     * @param appInfo
     * @return
     */
    public Observable<DownloadEvent> isAppInstalled(Context context, AppInfo appInfo){

        DownloadEvent event = new DownloadEvent();

        event.setFlag(AppUtils.isInstalled(context,appInfo.getPackageName())? DownloadFlag.INSTALLED:DownloadFlag.UN_INSTALL);

        return  Observable.just(event);

    }

    /**
     * 判断apk文件是否存在
     * @param context
     * @param appInfo
     * @return
     */
    public  Observable<DownloadEvent> isApkFileExsit(Context context,AppInfo appInfo){

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + "/" + appInfo.getReleaseKeyHash()+".apk";

        File file = new File(path);

        DownloadEvent event = new DownloadEvent();

        event.setFlag(file.exists()? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);

        return  Observable.just(event);

    }

    /**
     * 根据id获取应用下载信息
     * @param appInfo
     * @return
     */
    public Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo){

        return  mApiService.getAppDownloadInfo(appInfo.getId()).compose(RxHttpResponseCompose.composeSchedulers());

    }

    public Observable<DownloadEvent> receiveDownloadStatus(String url){

        return  mRxDownload.receiveDownloadStatus(url);

    }

    /**
     * 运行app
     * @param context
     * @param packageName
     */
    private void runApp(Context context,String packageName) {

        AppUtils.runApp(context,packageName);

    }

    /**
     * 暂停下载
     * @param url
     */
    private void pausedDownload(String  url) {

        mRxDownload.pauseServiceDownload(url).subscribe();

    }

    /**
     * 安装app
     * @param context
     * @param appInfo
     */
    private void installApp(Context context,AppInfo appInfo) {

        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + "/" + appInfo.getReleaseKeyHash()+".apk";

        AppUtils.installApk(context,path);

    }

    /**
     * AppInfo转DownloadBean
     * @param info
     * @return
     */
    private DownloadBean appInfo2DownloadBean(AppInfo info){

        DownloadBean downloadBean = new DownloadBean();

        downloadBean.setUrl(info.getAppDownloadInfo().getDownloadUrl());
        downloadBean.setSaveName(info.getReleaseKeyHash() +".apk");


        downloadBean.setExtra1(info.getId()+"");
        downloadBean.setExtra2(info.getIcon());
        downloadBean.setExtra3(info.getDisplayName());
        downloadBean.setExtra4(info.getPackageName());
        downloadBean.setExtra5(info.getReleaseKeyHash());

        return  downloadBean;
    }

    /**
     * 下载
     * @param btn
     * @param info
     */
    private void download(DownloadProgressButton btn,AppInfo info){


        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();

        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl()).subscribe(new DownloadConsumer(btn,info));

    }

    private void startDownload(final DownloadProgressButton btn, final AppInfo appInfo) {

        PermissionUtil.requestPermisson(btn.getContext(),WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e("testdown","aBoolean="+aBoolean);

                        if (aBoolean){
                            final AppDownloadInfo downloadInfo = appInfo.getAppDownloadInfo();
                            if (downloadInfo==null){
                                getAppDownloadInfo(appInfo).subscribe(new Consumer<AppDownloadInfo>() {
                                    @Override
                                    public void accept(AppDownloadInfo appDownloadInfo) throws Exception {

                                        Log.e("testdown","appDownloadInfo="+appDownloadInfo.getDownloadUrl());

                                        appInfo.setAppDownloadInfo(appDownloadInfo);

                                        download(btn,appInfo);
                                    }
                                });

                            }else {
                                download(btn,appInfo);
                            }
                        }
                    }
                });

    }


    class  DownloadConsumer implements Consumer<DownloadEvent> {

        DownloadProgressButton btn ;

        AppInfo mAppInfo;

        public DownloadConsumer(DownloadProgressButton b,AppInfo appInfo){

            this.btn = b;

            this.mAppInfo = appInfo;

        }

        @Override
        public void accept(@NonNull DownloadEvent event) throws Exception {

            int flag = event.getFlag();

            btn.setTag(R.id.tag_apk_flag,flag);

            bindClick(btn,mAppInfo);

            switch (flag){

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

            }

        }
    }

}

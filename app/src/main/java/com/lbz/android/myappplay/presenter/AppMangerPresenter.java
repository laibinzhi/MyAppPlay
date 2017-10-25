package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.presenter.contract.AppManagerContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by elitemc on 2017/9/21.
 */

public class AppMangerPresenter extends BasePresenter<AppManagerContract.IAppManagerModel,AppManagerContract.AppManagerView> {


    @Inject
    public AppMangerPresenter(AppManagerContract.IAppManagerModel mModel, AppManagerContract.AppManagerView mView) {
        super(mModel, mView);
    }


    public void getDownlodingApps(){

        mModel.getDownloadRecord().compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ProgressSubcriber<List<DownloadRecord>>(mContext,mView) {
                    @Override
                    public void onNext(List<DownloadRecord> downloadRecords) {

                        mView.showDownloading(downloadRecordFilter(downloadRecords));

                    }
                });
    }

    public void getLocalApks(){

        mModel.getLocalApks().compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ProgressSubcriber<List<AndroidApk>>(mContext,mView) {
                    @Override
                    public void onNext(List<AndroidApk> androidApks) {
                        mView.showApps(androidApks);
                    }
                });
    }

    public void getInstallApps(){

        mModel.getInstallApps().compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ProgressSubcriber<List<AndroidApk>>(mContext,mView) {
                    @Override
                    public void onNext(List<AndroidApk> androidApks) {
                        mView.showApps(androidApks);
                    }
                });
    }

    public void getUpdateApps(boolean update){

        mModel.getCanUpdateApps(update)
                .subscribe(new ProgressSubcriber<PageBean>(mContext, mView) {
                    @Override
                    public void onNext(@NonNull PageBean pageBean) {
                        mView.showUpdateApps(pageBean.getListApp());
                    }
                });

    }

    private List<DownloadRecord> downloadRecordFilter(List<DownloadRecord> downloadRecords){

        List<DownloadRecord> newList = new ArrayList<>();
        for (DownloadRecord r :downloadRecords){

            if(r.getFlag() != DownloadFlag.COMPLETED){

                newList.add(r);
            }
        }

        return newList;

    }
}

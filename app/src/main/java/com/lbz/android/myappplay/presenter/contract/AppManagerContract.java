package com.lbz.android.myappplay.presenter.contract;


import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by Ivan on 2017/1/3.
 */

public interface AppManagerContract {

    interface IAppManagerModel{

        Observable<List<DownloadRecord>> getDownloadRecord();

        Observable<List<AndroidApk>> getLocalApks();

    }

    interface AppManagerView extends BaseView {

        void showDownloading(List<DownloadRecord> downloadRecords);

        void showApps(List<AndroidApk> apps);

    }

}

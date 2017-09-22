package com.lbz.android.myappplay.data;

import android.content.Context;

import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.presenter.contract.AppManagerContract;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by elitemc on 2017/9/21.
 */

public class AppManagerModel implements AppManagerContract.IAppManagerModel {

    private  RxDownload mRxDownload;

    private Context mContext;

    public AppManagerModel(Context context,RxDownload rxDownload){

        this.mContext =context;

        mRxDownload = rxDownload;

    }

    @Override
    public Observable<List<DownloadRecord>> getDownloadRecord() {

        return mRxDownload.getTotalDownloadRecords();

    }

    @Override
    public Observable<List<AndroidApk>> getLocalApks() {

        final String dir = ACache.get(mContext).getAsString(Constant.APK_DOWNLOAD_DIR);

        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {

                e.onNext(scanApks(dir));
                e.onComplete();
            }
        });
    }

    private List<AndroidApk> scanApks(String dir){

        File file = new File(dir);

        if(!file.isDirectory()){

            throw  new RuntimeException("is not Dir");
        }

        File[] apks =  file.listFiles(new FileFilter(){

            @Override
            public boolean accept(File f) {

                if(f.isDirectory()){
                    return  false;
                }

                return f.getName().endsWith(".apk");
            }
        });

        List<AndroidApk>  androidApks = new ArrayList<>();

        for (File apk : apks){

            AndroidApk androidApk = AndroidApk.read(mContext,apk.getPath());

            if (androidApk!=null){

                androidApks.add(androidApk);

            }

        }

        return androidApks;

    }

}

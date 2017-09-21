package com.lbz.android.myappplay.di.module;

import android.app.Application;
import android.os.Environment;

import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.util.ACache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zlc.season.rxdownload2.RxDownload;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

@Module
public class DownloadModule {

    @Provides
    @Singleton
    public RxDownload provideRxDownload(Application application, File downDir){


        ACache.get(application).put(Constant.APK_DOWNLOAD_DIR,downDir.getPath());

       return RxDownload.getInstance(application)
                .defaultSavePath(downDir.getPath())
                .maxDownloadNumber(10)
                .maxThread(10);
    }


    @Singleton
    @Provides
    File provideDownloadDir(){

        return Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);

    }

}

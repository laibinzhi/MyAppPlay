package com.lbz.android.myappplay.di.component;

import android.app.Application;

import com.lbz.android.myappplay.bean.SearchHistory.SearchHistoryDao;
import com.lbz.android.myappplay.commom.rx.RxErrorHandle;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.data.http.CacheProviders;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.di.module.AppModule;
import com.lbz.android.myappplay.di.module.DBModule;
import com.lbz.android.myappplay.di.module.DownloadModule;
import com.lbz.android.myappplay.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by lbz on 2017/7/13.
 */
@Component(modules = {AppModule.class, HttpModule.class, DBModule.class,DownloadModule.class})
@Singleton
public interface AppComponent {

    ApiService getApiService();

    OkHttpClient getOkHttpClient();

    RxErrorHandle getRxErrorHandle();

    SearchHistoryDao getSearchHistoryDao();

    RxDownload getRxDownload();

    Application getApplication();

    CacheProviders getCacheProviders();

    Repository getRepository();

}

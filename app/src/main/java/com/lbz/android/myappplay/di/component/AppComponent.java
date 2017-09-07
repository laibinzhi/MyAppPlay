package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.commom.rx.RxErrorHandle;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.di.module.AppModule;
import com.lbz.android.myappplay.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by elitemc on 2017/7/13.
 */
@Component(modules = {AppModule.class, HttpModule.class})
@Singleton
public interface AppComponent {

    ApiService getApiService();

    OkHttpClient getOkHttpClient();

    RxErrorHandle getRxErrorHandle();

}

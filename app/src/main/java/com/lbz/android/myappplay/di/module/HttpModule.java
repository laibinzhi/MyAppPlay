package com.lbz.android.myappplay.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.lbz.android.myappplay.commom.http.CommonParamsInterceptor;
import com.lbz.android.myappplay.commom.rx.RxErrorHandle;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.data.http.CacheProviders;
import com.lbz.android.myappplay.data.http.Repository;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by lbz on 2017/7/13.
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application, Gson gson) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new CommonParamsInterceptor(application, gson))
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);
        return builder.build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    RxErrorHandle provideRxErrorHandle(Application application) {
        return new RxErrorHandle(application);
    }

    @Provides
    @Singleton
    CacheProviders provideCacheProviders(Application application) {
        return new RxCache.Builder()
                .persistence(application.getCacheDir(), new GsonSpeaker())
                .using(CacheProviders.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(CacheProviders cacheProviders, ApiService apiService) {
        return new Repository(cacheProviders, apiService);
    }

}

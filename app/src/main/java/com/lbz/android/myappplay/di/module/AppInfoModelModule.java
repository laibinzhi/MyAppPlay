package com.lbz.android.myappplay.di.module;

import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.data.http.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lbz on 2017/9/12.
 */

@Module
public class AppInfoModelModule {

    @Provides
    AppInfoModel provideAppInfoModel(ApiService apiService) {
        return new AppInfoModel(apiService);
    }

}

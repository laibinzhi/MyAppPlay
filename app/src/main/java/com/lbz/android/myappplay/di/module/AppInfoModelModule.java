package com.lbz.android.myappplay.di.module;

import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.data.http.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lbz on 2017/9/12.
 */

@Module
public class AppInfoModelModule {

    @Provides
    AppInfoModel provideAppInfoModel(Repository repository) {
        return new AppInfoModel(repository);
    }

}

package com.lbz.android.myappplay.di.module;

import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lbz on 2017/9/12.
 */

@Module(includes = AppInfoModelModule.class)
public class AppDetailModule {

    private AppInfoContract.AppDetailView mView;

    public AppDetailModule(AppInfoContract.AppDetailView view) {
        this.mView = view;
    }

    @Provides
    AppInfoContract.AppDetailView provideView() {
        return mView;
    }

}

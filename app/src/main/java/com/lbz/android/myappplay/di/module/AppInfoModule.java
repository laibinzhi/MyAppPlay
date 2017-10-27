package com.lbz.android.myappplay.di.module;


import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lbz on 2017/7/13.
 */
@Module(includes = AppInfoModelModule.class)
public class AppInfoModule {

    private AppInfoContract.AppInfoView mView;

    public AppInfoModule(AppInfoContract.AppInfoView view) {
        this.mView = view;
    }

    @Provides
    AppInfoContract.AppInfoView provideView() {
        return mView;
    }

}

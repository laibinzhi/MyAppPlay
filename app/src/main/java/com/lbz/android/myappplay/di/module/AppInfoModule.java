package com.lbz.android.myappplay.di.module;


import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by elitemc on 2017/7/13.
 */
@Module
public class AppInfoModule {

    private AppInfoContract.AppInfoView mView;

    public AppInfoModule(AppInfoContract.AppInfoView view) {
        this.mView = view;
    }

    @Provides
    AppInfoContract.AppInfoView provideView() {
        return mView;
    }

    @Provides
    AppInfoModel provideRecommendModel(ApiService apiService) {
        return new AppInfoModel(apiService);
    }

}

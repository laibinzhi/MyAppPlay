package com.lbz.android.myappplay.di.module;


import android.app.Application;

import com.lbz.android.myappplay.data.AppManagerModel;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.presenter.contract.AppManagerContract;

import dagger.Module;
import dagger.Provides;
import zlc.season.rxdownload2.RxDownload;


@Module
public class AppManagerModule {

    private AppManagerContract.AppManagerView mView;

    public AppManagerModule(AppManagerContract.AppManagerView view) {


        this.mView = view;
    }


    @Provides
    public AppManagerContract.AppManagerView provideView() {

        return mView;
    }


    @Provides
    public AppManagerContract.IAppManagerModel privodeModel(Application application , RxDownload rxDownload, Repository repository) {

        return new AppManagerModel(application,rxDownload,repository);

    }

}

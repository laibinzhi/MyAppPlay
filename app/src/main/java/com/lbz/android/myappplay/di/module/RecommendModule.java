package com.lbz.android.myappplay.di.module;

import android.app.ProgressDialog;

import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.fragment.RecomendFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by elitemc on 2017/7/13.
 */
@Module(includes = AppInfoModelModule.class)
public class RecommendModule {

    private AppInfoContract.View mView;

    public RecommendModule(AppInfoContract.View view) {
        this.mView = view;
    }

    @Provides
    AppInfoContract.View provideView() {
        return mView;
    }

    @Provides
    ProgressDialog provideProgressDialog(AppInfoContract.View view) {
        return new ProgressDialog(((RecomendFragment) view).getActivity());
    }

}

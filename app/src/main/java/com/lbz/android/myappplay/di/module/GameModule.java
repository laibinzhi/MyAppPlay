package com.lbz.android.myappplay.di.module;

import android.app.ProgressDialog;

import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.fragment.RecomendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by elitemc on 2017/7/13.
 */
@Module(includes = AppInfoModelModule.class)
public class GameModule {

    private AppInfoContract.View mView;

    public GameModule(AppInfoContract.View view) {
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

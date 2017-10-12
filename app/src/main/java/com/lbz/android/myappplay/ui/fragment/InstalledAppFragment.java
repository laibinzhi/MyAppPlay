package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppManagerComponent;
import com.lbz.android.myappplay.di.module.AppManagerModule;
import com.lbz.android.myappplay.ui.adapter.InstallAppAdapter;

import java.util.List;

/**
 * Created by elitemc on 2017/9/25.
 */

public class InstalledAppFragment extends  AppManangerFragment {

    InstallAppAdapter mAdapter;

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent)
                .appManagerModule(new AppManagerModule(this))
                .build().injectInstalledAppFragment(this);
    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {
        mAdapter = new InstallAppAdapter();
        return mAdapter;
    }

    @Override
    protected void loadData() {
        mPresenter.getInstallApps();
    }

    @Override
    public void showApps(List<AndroidApk> apps) {

        mAdapter.setNewData(apps);

    }

    @Override
    public void showUpdateApps(List<AppInfo> apps) {

    }

}

package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.RecyclerView;


import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppManagerComponent;
import com.lbz.android.myappplay.di.module.AppManagerModule;
import com.lbz.android.myappplay.ui.adapter.DownloadedAdapter;

import java.util.List;



public class DownloadedFragment extends AppManangerFragment{

    DownloadedAdapter mAdapter;

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder().appComponent(appComponent)
                .appManagerModule(new AppManagerModule(this))
                .build().injectDownloadedFragment(this);
    }

    @Override
    public void init() {
        super.init();

        mPresenter.getLocalApks();

    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {
        mAdapter = new DownloadedAdapter();
        return mAdapter;
    }



    @Override
    public void showApps(List<AndroidApk> apps) {

        mAdapter.addData(apps);

    }
}

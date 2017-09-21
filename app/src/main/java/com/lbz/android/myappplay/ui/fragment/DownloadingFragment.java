package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppManagerComponent;
import com.lbz.android.myappplay.di.module.AppManagerModule;
import com.lbz.android.myappplay.ui.adapter.DownloadingAdapter;

import java.util.List;

import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by elitemc on 2017/9/21.
 */

public class DownloadingFragment extends AppManangerFragment{

    private DownloadingAdapter mAdapter;

    @Override
    public void init() {
        super.init();

        mPresenter.getDownlodingApps();

    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {
        mAdapter = new DownloadingAdapter(mMyApplication);
        return mAdapter;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {

        DaggerAppManagerComponent.builder().appComponent(appComponent)
                .appManagerModule(new AppManagerModule(this))
                .build().injectDownloadingFragment(this);
    }

    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {

        mAdapter.addData(downloadRecords);

    }

}

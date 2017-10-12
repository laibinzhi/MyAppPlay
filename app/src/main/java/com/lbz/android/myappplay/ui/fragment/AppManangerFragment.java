package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.presenter.AppMangerPresenter;
import com.lbz.android.myappplay.presenter.contract.AppManagerContract;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import zlc.season.rxdownload2.entity.DownloadRecord;



public abstract class AppManangerFragment extends ProgressFragment<AppMangerPresenter>  implements AppManagerContract.AppManagerView {



    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private boolean isPrepared;

    protected boolean isVisible;

    @Override
    public void init() {

        setupRecyclerView();

        isPrepared = true;

        lazyLoad();

    }

    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            isPrepared = false;
        }
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    private void setupRecyclerView() {


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);


        mRecyclerView.setAdapter(setupAdapter());
    }

    @Override
    public void showApps(List<AndroidApk> apps) {

    }

    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {

    }

    protected abstract RecyclerView.Adapter setupAdapter();

    protected abstract void loadData();

}

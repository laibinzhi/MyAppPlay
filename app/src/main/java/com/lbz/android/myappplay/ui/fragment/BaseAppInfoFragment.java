package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.presenter.AppInfoPresenter;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/4.
 */

public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.AppInfoView, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AppInfoAdapter mAppInfoAdapter;

    int page = 0;

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void init() {
        initRecyclerView();
        mPresenter.requestData(type(), page);
    }

    private void initRecyclerView() {
        mAppInfoAdapter = buildAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAppInfoAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAppInfoAdapter);
    }

    @Override
    public void showData(PageBean<AppInfo> pageBean) {
        mAppInfoAdapter.addData(pageBean.getDatas());
        if (pageBean.isHasMore()) {
            page++;
        }
        mAppInfoAdapter.setEnableLoadMore(pageBean.isHasMore());
    }

    @Override
    public void onLoadMoreComplete() {
        mAppInfoAdapter.loadMoreComplete();

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(type(), page);
    }

    abstract int type();

    abstract AppInfoAdapter buildAdapter();


}

package com.lbz.android.myappplay.ui.fragment;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerGameComponent;
import com.lbz.android.myappplay.di.module.GameModule;
import com.lbz.android.myappplay.presenter.GamePresenter;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.adapter.GamesIndexAdapter;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/7/11.
 */
public class GamesFragment extends ProgressFragment<GamePresenter> implements AppInfoContract.View {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    GamesIndexAdapter mAdapter;

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerGameComponent.builder().appComponent(appComponent)
                .gameModule(new GameModule(this))
                .build().inject(this);
    }

    @Override
    protected void init() {
        initRecyclerView();
        mPresenter.requestDatas();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onEmptyViewClick() {
        mPresenter.requestDatas();
    }

    @Override
    public void showData(PageBean pageBean) {

        mAdapter = new GamesIndexAdapter(getActivity(),mMyApplication);

        mAdapter.setData(pageBean);

        mRecyclerView.setAdapter(mAdapter);
    }
}

package com.lbz.android.myappplay.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.util.SoftKeyboardUtil;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerSearchAppComponent;
import com.lbz.android.myappplay.di.module.SearchAppModule;
import com.lbz.android.myappplay.presenter.SearchAppPresenter;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;
import com.lbz.android.myappplay.ui.activity.AppDetailActivity;
import com.lbz.android.myappplay.ui.activity.SearchAppActivity;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/15.
 */

public class KeyWordAppListFragment extends ProgressFragment<SearchAppPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, SearchAppContract.SearchAppView {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.empty_text)
    TextView mEmpty;

    private AppInfoAdapter mAppInfoAdapter;

    int page = 0;

    private String keyword;

    @Override
    protected void init() {
        initRecyclerView();
        mPresenter.AppListByKeyword(getKeyWord(), page);
    }

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view_with_empty_view;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerSearchAppComponent.builder().appComponent(appComponent)
                .searchAppModule(new SearchAppModule(this))
                .build().KeyWordAppListFragment(this);
    }

    private void initRecyclerView() {
        mAppInfoAdapter = AppInfoAdapter.builder().showPosition(false).showBrief(true).showCategoryName(false).build();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAppInfoAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAppInfoAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMyApplication.setView(view);
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                AppInfo appInfo = mAppInfoAdapter.getItem(position);
                intent.putExtra("appinfo", appInfo);
                startActivity(intent);
            }
        });
    }

    public String getKeyWord() {
        return this.keyword;
    }

    public void setKeyWord(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.AppListByKeyword(getKeyWord(), page);
    }


    @Override
    public void showAppList(PageBean pageBean) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmpty.setVisibility(View.GONE);
        SoftKeyboardUtil.hide(getActivity());
        mAppInfoAdapter.addData(pageBean.getListApp());
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
    public void showNoDataView(String msg) {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setText(msg);
        mEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAssociationalList(List<String> associationalList) {

    }

    @Override
    public void showHistoryList(List<String> history) {

    }

    @Override
    public void showAssociationalFragment() {

    }

    @Override
    public void showSearchHistoryFragment() {

    }
}

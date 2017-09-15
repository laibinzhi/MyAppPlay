package com.lbz.android.myappplay.ui.fragment;

import android.widget.ImageView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerSearchAppComponent;
import com.lbz.android.myappplay.di.module.SearchAppModule;
import com.lbz.android.myappplay.presenter.SearchAppPresenter;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;
import com.lbz.android.myappplay.ui.adapter.ShowAppHistoryAdapter;
import com.lbz.android.myappplay.ui.widget.SourcePanelGridView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/14.
 */

public class SearchHistoryFragment extends BaseFragment<SearchAppPresenter> implements SearchAppContract.SearchAppView {

    ShowAppHistoryAdapter mAdapter;

    @Bind(R.id.delete_history_btn)
    ImageView mDeleteBtn;
    @Bind(R.id.search_grid_view)
    SourcePanelGridView mSearchGridView;

    @Override
    protected void init() {
        mPresenter.getHistoryWordList();
        mAdapter =new ShowAppHistoryAdapter(getActivity());
        mSearchGridView.setAdapter(mAdapter);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerSearchAppComponent.builder().appComponent(appComponent)
                .searchAppModule(new SearchAppModule(this))
                .build().injectSearchHistoryFragment(this);

    }

    @Override
    public void showHistoryList(List<String> history) {
        mAdapter.setData(history);
    }

    @Override
    public void showAssociationalFragment() {

    }

    @Override
    public void showSearchHistoryFragment() {

    }


    @Override
    public void showAssociationalList(List<String> associationalList) {

    }

    @Override
    public void showNoDataView(String msg) {

    }

    @Override
    public void showAppList(PageBean pageBean) {

    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {

    }
}

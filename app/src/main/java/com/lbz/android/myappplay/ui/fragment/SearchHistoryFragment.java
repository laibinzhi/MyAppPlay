package com.lbz.android.myappplay.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerSearchAppComponent;
import com.lbz.android.myappplay.di.module.SearchAppModule;
import com.lbz.android.myappplay.presenter.SearchAppPresenter;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;
import com.lbz.android.myappplay.ui.activity.SearchAppActivity;
import com.lbz.android.myappplay.ui.adapter.ShowAppHistoryAdapter;
import com.lbz.android.myappplay.ui.widget.SourcePanelGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by elitemc on 2017/9/14.
 */

public class SearchHistoryFragment extends BaseFragment<SearchAppPresenter> implements SearchAppContract.SearchAppView,AdapterView.OnItemClickListener {

    ShowAppHistoryAdapter mAdapter;

    @Bind(R.id.delete_history_btn)
    ImageView mDeleteBtn;
    @Bind(R.id.search_grid_view)
    SourcePanelGridView mSearchGridView;

    @Override
    protected void init() {
        mAdapter = new ShowAppHistoryAdapter(getActivity());
        mSearchGridView.setAdapter(mAdapter);
        mSearchGridView.setOnItemClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.getHistoryWordList();
        }

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

    @OnClick(R.id.delete_history_btn)
    public void onViewClicked() {

        mPresenter.deleteAllHistory();

        mPresenter.getHistoryWordList();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchAppActivity.notRuestAssociational = true;
        ((SearchAppActivity) getActivity()).mSearchTextView.setText( mAdapter.getItem(position).toString());
        ((EditText)(((SearchAppActivity) getActivity()).mSearchTextView)).setSelection(((SearchAppActivity) getActivity()).mSearchTextView.getText().length());
        String searchWord = mAdapter.getItem(position).toString();
        ((SearchAppActivity) getActivity()).initKeyWordAppListFragment(searchWord);
    }
}

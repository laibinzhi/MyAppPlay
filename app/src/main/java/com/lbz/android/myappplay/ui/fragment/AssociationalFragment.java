package com.lbz.android.myappplay.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerSearchAppComponent;
import com.lbz.android.myappplay.di.module.SearchAppModule;
import com.lbz.android.myappplay.presenter.SearchAppPresenter;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;
import com.lbz.android.myappplay.ui.activity.SearchAppActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/14.
 */

public class AssociationalFragment extends BaseFragment<SearchAppPresenter> implements SearchAppContract.SearchAppView {

    ArrayAdapter mAdapter;
    private ArrayList<String> mList;

    @Bind(R.id.list_view)
    ListView mListView;

    @Override
    protected int setLayout() {
        return R.layout.template_list_view;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerSearchAppComponent.builder().appComponent(appComponent)
                .searchAppModule(new SearchAppModule(this))
                .build().injectShowAssociationalFragment(this);
    }

    @Override
    protected void init() {
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchAppActivity.notRuestAssociational = true;
                ((SearchAppActivity) getActivity()).mSearchTextView.setText( mAdapter.getItem(position).toString());
                ((EditText)(((SearchAppActivity) getActivity()).mSearchTextView)).setSelection(((SearchAppActivity) getActivity()).mSearchTextView.getText().length());
                String searchWord = mAdapter.getItem(position).toString();
                ((SearchAppActivity) getActivity()).initKeyWordAppListFragment(searchWord);
            }
        });

    }

    @Override
    public void showAssociationalList(List<String> associationalList) {
        mList.clear();
        mList.addAll(associationalList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAppList(PageBean pageBean) {

    }

    @Override
    public void onLoadMoreComplete() {

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

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoDataView(String msg) {

    }
}

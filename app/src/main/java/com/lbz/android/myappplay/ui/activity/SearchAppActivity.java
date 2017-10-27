package com.lbz.android.myappplay.ui.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerSearchAppComponent;
import com.lbz.android.myappplay.di.module.SearchAppModule;
import com.lbz.android.myappplay.presenter.SearchAppPresenter;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;
import com.lbz.android.myappplay.ui.fragment.KeyWordAppListFragment;
import com.lbz.android.myappplay.ui.fragment.SearchHistoryFragment;
import com.lbz.android.myappplay.ui.fragment.AssociationalFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lbz on 2017/9/14.
 */

public class SearchAppActivity extends BaseActivity<SearchAppPresenter> implements SearchView.OnQueryTextListener, SearchAppContract.SearchAppView {

    @Bind(R.id.searchView)
    SearchView mSearchView;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.view_content)
    FrameLayout viewContent;
    public TextView mSearchTextView;
    SearchHistoryFragment mHistoryFragment;
    AssociationalFragment mAssociationalFragment;
    KeyWordAppListFragment mKeyWordAppListFragment;
    public static boolean notRuestAssociational = false ;//SearchView监听TextView是否需要请求联想词数据的flag

    @Override
    protected int setLayout() {
        return R.layout.activity_search_app;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {
        DaggerSearchAppComponent.builder().appComponent(appComponent)
                .searchAppModule(new SearchAppModule(this))
                .build().injectSearchAppActivity(this);
    }

    @Override
    protected void init() {

        initToolbar();
        initSearchView();
        initSearchHistoryFragment();
    }

    private void initSearchHistoryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mHistoryFragment == null) {
            mHistoryFragment = new SearchHistoryFragment();
            transaction.add(R.id.view_content, mHistoryFragment);
        }
        hideFragment(transaction);
        transaction.show(mHistoryFragment);
        transaction.commit();
    }

    private void initAssociationalFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mAssociationalFragment == null) {
            mAssociationalFragment = new AssociationalFragment();
            transaction.add(R.id.view_content, mAssociationalFragment);
        }
        hideFragment(transaction);
        transaction.show(mAssociationalFragment);
        transaction.commit();
    }

    public void initKeyWordAppListFragment(String keyWord) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mKeyWordAppListFragment != null) {
            if (mKeyWordAppListFragment.isAdded()) {
                transaction.remove(mKeyWordAppListFragment);
            }
            mKeyWordAppListFragment = null;
        }
        mKeyWordAppListFragment = new KeyWordAppListFragment();
        transaction.add(R.id.view_content, mKeyWordAppListFragment);
        mKeyWordAppListFragment.setKeyWord(keyWord);
        hideFragment(transaction);
        transaction.show(mKeyWordAppListFragment);
        transaction.commit();
        presenter.insertHistory(keyWord);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mHistoryFragment != null) {
            transaction.hide(mHistoryFragment);
        }
        if (mAssociationalFragment != null) {
            transaction.hide(mAssociationalFragment);
        }
        if (mKeyWordAppListFragment != null) {
            transaction.hide(mKeyWordAppListFragment);
        }
    }

    private void initSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("Search");

        mSearchTextView = (TextView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mSearchTextView.setTextColor(Color.WHITE);

        try {
            Class<?> argClass = mSearchView.getClass();
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(mSearchView);
            mView.setBackgroundResource(R.drawable.search_view_radius_bg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSearchView.setOnQueryTextListener(this);
        presenter.requestAssociational(mSearchTextView,false);
    }

    private void initToolbar() {

        mToolBar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 0) {
            initKeyWordAppListFragment(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showAssociationalList(List<String> associationalList) {

        mAssociationalFragment.showAssociationalList(associationalList);

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
    public void showHistoryList(List<String> history) {

    }

    @Override
    public void showAssociationalFragment() {
        initAssociationalFragment();
    }

    @Override
    public void showSearchHistoryFragment() {
        initSearchHistoryFragment();
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

package com.lbz.android.myappplay.ui.fragment;

import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppInfoComponent;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;


public class CategoryAppFragment extends BaseAppInfoFragment {

    private int categoryId;
    private int mFlagType;

    public CategoryAppFragment(int categoryId, int flagType) {

        this.categoryId = categoryId;
        this.mFlagType = flagType;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {

        DaggerAppInfoComponent.builder().appComponent(appComponent).appInfoModule(new AppInfoModule(this))
                .build().injectCategoryAppFragment(this);
    }

    @Override
    public void init() {


        mPresenter.requestCategoryApps(categoryId, page, mFlagType);
        initRecyclerView();

    }

    @Override
    int type() {
        return 0;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(false).showBrief(true).showCategoryName(false).build();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestCategoryApps(categoryId, page, mFlagType);
    }
}

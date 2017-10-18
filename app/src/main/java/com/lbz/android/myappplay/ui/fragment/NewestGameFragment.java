package com.lbz.android.myappplay.ui.fragment;

import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppInfoComponent;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;

/**
 * Created by elitemc on 2017/9/8.
 */

public class NewestGameFragment extends BaseAppInfoFragment {

    public static final int GAME_CATEGORY = 15;
    public static final int GAME_CATEGORY_TOPLIST = 1;
    public static final int GAME_CATEGORY_NEW = 2;

    @Override
    protected void init() {
        mPresenter.requestCategoryApps(GAME_CATEGORY, page, GAME_CATEGORY_NEW);
        initRecyclerView();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestCategoryApps(GAME_CATEGORY, page, GAME_CATEGORY_NEW);
    }

    @Override
    int type() {
        return 0;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(false).showBrief(true).showCategoryName(false).application(mMyApplication).build();
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectNewestGameFragment(this);
    }
}

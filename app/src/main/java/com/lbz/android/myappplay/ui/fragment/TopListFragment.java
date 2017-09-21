package com.lbz.android.myappplay.ui.fragment;


import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppInfoComponent;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.presenter.AppInfoPresenter;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;

/**
 * Created by elitemc on 2017/7/11.
 */
public class TopListFragment extends BaseAppInfoFragment{


    @Override
    int type() {
        return AppInfoPresenter.TOP_LIST;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(true).showBrief(false).showCategoryName(true).application(mMyApplication).build();
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectTopListFragment(this);
    }
}

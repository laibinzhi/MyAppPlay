package com.lbz.android.myappplay.ui.fragment;

import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppInfoComponent;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;

import static com.lbz.android.myappplay.presenter.AppInfoPresenter.HOT_APP_LIST;

/**
 * Created by elitemc on 2017/9/8.
 */

public class HotAppFragment extends BaseAppInfoFragment {

    @Override
    int type() {
        return HOT_APP_LIST;
    }

    @Override
    AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showPosition(false).showBrief(true).showCategoryName(false).build();
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectHotAppFragment(this);
    }
}

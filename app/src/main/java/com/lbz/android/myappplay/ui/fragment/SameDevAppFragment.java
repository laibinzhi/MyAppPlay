package com.lbz.android.myappplay.ui.fragment;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppInfoComponent;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.presenter.AppInfoPresenter;
import com.lbz.android.myappplay.ui.activity.SameDevAppActivity;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;

/**
 * Created by elitemc on 2017/9/13.
 */

public class SameDevAppFragment extends BaseAppInfoFragment{

    private int appId;

    public SameDevAppFragment(int appId) {

        this.appId = appId;

    }

    @Override
    int type() {
        return AppInfoPresenter.SAME_DEV_APP_LIST;
    }

    @Override
    protected void init() {
        initRecyclerView();
        mPresenter.requestSameDevApps(appId,0);
    }

    @Override
    public void showData(PageBean pageBean) {
        super.showData(pageBean);
        ((SameDevAppActivity)getActivity()).setTitle(pageBean.getVdnm());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestSameDevApps(appId, page);
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
                .injectSameDevAppFragment(this);
    }
}

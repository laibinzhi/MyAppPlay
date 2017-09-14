package com.lbz.android.myappplay.ui.activity;

import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.bean.AppDetailBean;
import com.lbz.android.myappplay.ui.fragment.SameDevAppFragment;

/**
 * Created by lbz on 2017/9/13.
 */

public class SameDevAppActivity extends BaseFragmentActivity {

    private AppDetailBean mAppDetailBean;

    @Override
    protected void init() {
        this.mAppDetailBean = (AppDetailBean) getIntent().getSerializableExtra("appDetail");
        super.init();
    }

    @Override
    public Fragment getFirstFragment() {
        return new SameDevAppFragment(mAppDetailBean.getId());
    }

    @Override
    public CharSequence getTitleString() {

        return mAppDetailBean.getPublisherName();

    }

    public void setTitle(String msg){
        mToolBar.setTitle(msg);
    }


}

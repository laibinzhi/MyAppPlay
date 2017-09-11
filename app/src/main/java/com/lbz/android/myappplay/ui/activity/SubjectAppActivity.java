package com.lbz.android.myappplay.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.bean.SubjectBean;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.ui.fragment.SubjectAppFragment;

/**
 * Created by elitemc on 2017/9/7.
 */

public class SubjectAppActivity extends BaseFragmentActivity {

    private SubjectBean bean;

    @Override
    public Fragment getFirstFragment() {

        return new SubjectAppFragment(bean.getRelatedId(),bean.getMticon());

    }

    @Override
    public CharSequence getTitleString() {
        return bean.getTitle();
    }

    @Override
    protected void init() {
        getData();
        super.init();
    }

    private void getData() {

        Intent intent = getIntent();

        bean = (SubjectBean) intent.getSerializableExtra(Constant.SUBJECT);
    }


}

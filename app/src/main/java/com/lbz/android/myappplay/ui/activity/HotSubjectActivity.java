package com.lbz.android.myappplay.ui.activity;

import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.ui.fragment.HotSubjectFragment;

public class HotSubjectActivity extends BaseFragmentActivity {


    @Override
    public Fragment getFirstFragment() {
        return new HotSubjectFragment();
    }

    @Override
    public int getTitleId() {
        return R.string.hot_top;
    }

}

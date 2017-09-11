package com.lbz.android.myappplay.ui.activity;

import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.ui.fragment.HotAppFragment;

/**
 * Created by elitemc on 2017/9/7.
 */

public class HotAppActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFirstFragment() {

        return new HotAppFragment();

    }

    @Override
    public CharSequence getTitleString() {

        return getString(R.string.hot_app);

    }
}

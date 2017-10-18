package com.lbz.android.myappplay.ui.activity;

import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.ui.fragment.HotAppFragment;
import com.lbz.android.myappplay.ui.fragment.HotRankGameFragment;

/**
 * Created by elitemc on 2017/9/7.
 */

public class HotRankGameActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFirstFragment() {

        return new HotRankGameFragment();

    }

    @Override
    public CharSequence getTitleString() {

        return getString(R.string.hot_game);

    }
}

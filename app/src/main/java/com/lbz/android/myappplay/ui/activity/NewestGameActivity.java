package com.lbz.android.myappplay.ui.activity;

import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.ui.fragment.HotAppFragment;
import com.lbz.android.myappplay.ui.fragment.NewestGameFragment;

/**
 * Created by lbz on 2017/9/7.
 */

public class NewestGameActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFirstFragment() {

        return new NewestGameFragment();

    }

    @Override
    public CharSequence getTitleString() {

        return getString(R.string.newest_game);

    }
}

package com.lbz.android.myappplay.ui.activity;

import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.ui.fragment.GameCategoryFragment;
import com.lbz.android.myappplay.ui.fragment.NewestGameFragment;

/**
 * Created by elitemc on 2017/9/7.
 */

public class GameCategoryActivity extends BaseFragmentActivity {

    @Override
    public Fragment getFirstFragment() {

        return new GameCategoryFragment();

    }

    @Override
    public CharSequence getTitleString() {

        return getString(R.string.game_category);

    }
}

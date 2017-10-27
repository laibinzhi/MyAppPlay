package com.lbz.android.myappplay.ui.fragment;

/**
 * Created by lbz on 2017/10/18.
 */

public class GameCategoryFragment extends CategoryFragment {

    public static final int GAME_CATEGORY = 15;

    @Override
    protected void init() {
        initRecycleView();
        mPresenter.requestDatas(GAME_CATEGORY,false);
    }
}

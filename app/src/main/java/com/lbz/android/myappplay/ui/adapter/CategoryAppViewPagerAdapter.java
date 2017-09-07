package com.lbz.android.myappplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.lbz.android.myappplay.ui.fragment.CategoryAppFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 2016/12/8.
 */

public class CategoryAppViewPagerAdapter extends FragmentStatePagerAdapter {



    private List<String> titles = new ArrayList<>(3);


    private int mCategoryId;
    public CategoryAppViewPagerAdapter(FragmentManager fm, int categoryid) {
        super(fm);
        this.mCategoryId = categoryid;

        titles.add("精品");
        titles.add("排行");
        titles.add("新品");
    }





    @Override
    public Fragment getItem(int position) {

        return new CategoryAppFragment(mCategoryId,position);

    }

    @Override
    public int getCount() {
        return titles.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

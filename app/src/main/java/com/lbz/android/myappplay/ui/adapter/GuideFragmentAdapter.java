package com.lbz.android.myappplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elitemc on 2017/7/13.
 */
public class GuideFragmentAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragmentList;

    public void setFragmentList(List<Fragment> fragmentList) {
        this.mFragmentList = fragmentList;
    }


    public GuideFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}

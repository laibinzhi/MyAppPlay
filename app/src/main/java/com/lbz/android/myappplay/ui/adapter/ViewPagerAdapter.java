package com.lbz.android.myappplay.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lbz.android.myappplay.bean.FragmentInfo;

import java.util.List;

/**
 * Created by elitemc on 2017/7/11.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragmentInfos;

    public ViewPagerAdapter(FragmentManager fm, List<FragmentInfo> fragments) {
        super(fm);
        this.mFragmentInfos =fragments;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) mFragmentInfos.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentInfos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentInfos.get(position).getTitle();
    }

}

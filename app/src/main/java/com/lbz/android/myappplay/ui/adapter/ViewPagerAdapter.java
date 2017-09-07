package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.FragmentInfo;
import com.lbz.android.myappplay.ui.fragment.CategoryFragment;
import com.lbz.android.myappplay.ui.fragment.GamesFragment;
import com.lbz.android.myappplay.ui.fragment.TopListFragment;
import com.lbz.android.myappplay.ui.fragment.RecomendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elitemc on 2017/7/11.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragmentInfo = new ArrayList<>(4);

    private Context mContext;


    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        initFragment();
    }

    private void initFragment() {
        mFragmentInfo.add(new FragmentInfo(mContext.getString(R.string.recomend), RecomendFragment.class));
        mFragmentInfo.add(new FragmentInfo(mContext.getString(R.string.ranking), TopListFragment.class));
        mFragmentInfo.add(new FragmentInfo(mContext.getString(R.string.games), GamesFragment.class));
        mFragmentInfo.add(new FragmentInfo(mContext.getString(R.string.category), CategoryFragment.class));
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) mFragmentInfo.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentInfo.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentInfo.get(position).getTitle();
    }
}

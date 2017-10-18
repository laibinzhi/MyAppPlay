package com.lbz.android.myappplay.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.FragmentInfo;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.ui.adapter.ViewPagerAdapter;
import com.lbz.android.myappplay.ui.fragment.DownloadedFragment;
import com.lbz.android.myappplay.ui.fragment.DownloadingFragment;
import com.lbz.android.myappplay.ui.fragment.InstalledAppFragment;
import com.lbz.android.myappplay.ui.fragment.UpdateAppFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/21.
 */

public class AppManagerActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    public int page = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_app_manager;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        initData();
        initToolbar();
        initTablayout();
    }

    private void initData() {
        page = getIntent().getIntExtra("page", 0);
    }

    private void initToolbar() {

        mToolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.setTitle(R.string.download_manager);
        mToolbar.setTitleTextColor(getColor(R.color.color_w));
    }

    private void initTablayout() {

        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), initFragments());
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        setPage();

    }

    private List<FragmentInfo> initFragments() {

        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo("下载", DownloadingFragment.class));
        mFragments.add(new FragmentInfo("已完成", DownloadedFragment.class));
        mFragments.add(new FragmentInfo("可升级", UpdateAppFragment.class));
        mFragments.add(new FragmentInfo("已安装", InstalledAppFragment.class));

        return mFragments;

    }

    public void setPage() {
        mViewPager.setCurrentItem(page);
    }
}

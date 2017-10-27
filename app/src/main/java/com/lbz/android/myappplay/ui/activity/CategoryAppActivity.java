package com.lbz.android.myappplay.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.Category;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.ui.adapter.CategoryAppViewPagerAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.Bind;

/**
 * Created by lbz on 2017/9/7.
 */

public class CategoryAppActivity extends BaseActivity{

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    private   Category category;


    @Override
    protected int setLayout() {
        return R.layout.activity_cateogry_app;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        getData();
        initTablayout();
    }

    private void getData() {

        Intent intent = getIntent();

        category = (Category) intent.getSerializableExtra(Constant.CATEGORY);
    }

    private void initTablayout() {

        mToolBar.setTitle(category.getName());

        mToolBar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        CategoryAppViewPagerAdapter adapter = new CategoryAppViewPagerAdapter(getSupportFragmentManager(),category.getId());
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);


    }
}

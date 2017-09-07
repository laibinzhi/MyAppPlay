package com.lbz.android.myappplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.ui.adapter.GuideFragmentAdapter;
import com.lbz.android.myappplay.ui.fragment.GuideFragment;
import com.lbz.android.myappplay.ui.widget.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.btn_enter)
    Button btnEnter;
    @Bind(R.id.indicator)
    CircleIndicator indicator;

    private GuideFragmentAdapter mGuideFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(GuideFragment.newInstance(R.drawable.guide_1, R.color.color_bg_guide1, R.string.guide_1));
        fragmentList.add(GuideFragment.newInstance(R.drawable.guide_2, R.color.color_bg_guide2, R.string.guide_2));
        fragmentList.add(GuideFragment.newInstance(R.drawable.guide_3, R.color.color_bg_guide3, R.string.guide_3));
        mGuideFragmentAdapter = new GuideFragmentAdapter(getSupportFragmentManager());
        mGuideFragmentAdapter.setFragmentList(fragmentList);

        viewpager.setAdapter(mGuideFragmentAdapter);

        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(mGuideFragmentAdapter.getCount());
        viewpager.setAdapter(mGuideFragmentAdapter);

        viewpager.addOnPageChangeListener(this);

        indicator.setViewPager(viewpager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        btnEnter.setVisibility((position == mGuideFragmentAdapter.getCount() - 1) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.btn_enter)
    public void enterMain(View view) {
        ACache.get(this).put(Constant.IS_SHOW_GUIDE,"0");
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }
}

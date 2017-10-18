package com.lbz.android.myappplay.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.ui.fragment.SettingFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/10/18.
 */

public class SettingActivity extends BaseActivity {

    @Bind(R.id.tool_bar)
    protected Toolbar mToolBar;

    public static final String FIRST_FRAG_TAG = "first_frag";

    @Override
    protected int setLayout() {
        return R.layout.activity_fragment_app_base;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        initToolbar();

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            try {
                this.loadFirstFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initToolbar() {
        mToolBar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );
        mToolBar.setTitle(R.string.sys_setting);
        mToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_w));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadFirstFragment() throws Exception {

        Fragment singleFragment = new SettingFragment();

        singleFragment.setRetainInstance(true);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, singleFragment, FIRST_FRAG_TAG);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

}

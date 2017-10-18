package com.lbz.android.myappplay.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.FragmentInfo;
import com.lbz.android.myappplay.bean.User;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.GlideCircleTransform;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.commom.util.PermissionUtil;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.ui.adapter.ViewPagerAdapter;
import com.lbz.android.myappplay.ui.fragment.CategoryFragment;
import com.lbz.android.myappplay.ui.fragment.GamesFragment;
import com.lbz.android.myappplay.ui.fragment.RecomendFragment;
import com.lbz.android.myappplay.ui.fragment.TopListFragment;
import com.lbz.android.myappplay.ui.typeface.LbzFont;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.reactivex.functions.Consumer;


public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    View headerView;


    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private ImageView mUserHeadView;
    private TextView mTextUserName;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull User user) throws Exception {

                initUserHeadView(user);
            }
        });

        PermissionUtil.requestPermisson(this, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {

                        if (aBoolean) {
                            initDrawerLayout();

                            initTabLayout();

                            initUser();
                        } else {
                            //------
                        }
                    }
                });

    }

    private void initTabLayout() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), initFragment());
        mViewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());

        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<FragmentInfo> initFragment() {
        List<FragmentInfo> mFragments = new ArrayList<>(4);

        mFragments.add(new FragmentInfo(getString(R.string.recomend), RecomendFragment.class));
        mFragments.add(new FragmentInfo(getString(R.string.ranking), TopListFragment.class));
        mFragments.add(new FragmentInfo(getString(R.string.games), GamesFragment.class));
        mFragments.add(new FragmentInfo(getString(R.string.category), CategoryFragment.class));

        return mFragments;

    }

    private void initDrawerLayout() {

        headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        mUserHeadView = (ImageView) headerView.findViewById(R.id.img_avatar);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, LbzFont.Icon.cniao_head).colorRes(R.color.white));

        mTextUserName = (TextView) headerView.findViewById(R.id.txt_username);


        mNavigationView.getMenu().findItem(R.id.menu_app_update).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_loop));
        mNavigationView.getMenu().findItem(R.id.menu_download_manager).setIcon(new IconicsDrawable(this, LbzFont.Icon.cniao_download));
        mNavigationView.getMenu().findItem(R.id.menu_app_uninstall).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline));
        mNavigationView.getMenu().findItem(R.id.menu_setting).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_ios_gear_outline));

        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, LbzFont.Icon.cniao_shutdown));


        mNavigationView.setNavigationItemSelectedListener(this);
        mToolbar.inflateMenu(R.menu.toolbar);
        mToolbar.setOnMenuItemClickListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchAppActivity.class));
                break;
            case R.id.action_download:
                openAppManager(0);
                break;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_app_update:

                openAppManager(2);

                break;

            case R.id.menu_download_manager:

                openAppManager(0);

                break;

            case R.id.menu_app_uninstall:

                openAppManager(3);

                break;

            case R.id.menu_logout:

                logout();

                break;
        }
        return false;
    }

    private void initUser() {

        Object objUser = ACache.get(this).getAsObject(Constant.USER);

        if (objUser == null) {

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });

        } else {

            User user = (User) objUser;
            initUserHeadView(user);
        }
    }


    private void initUserHeadView(User user) {

        Glide.with(this).load(user.getLogo_url()).transform(new GlideCircleTransform(this)).into(mUserHeadView);

        mTextUserName.setText(user.getUsername());
    }

    private void logout() {

        ACache aCache = ACache.get(this);

        aCache.put(Constant.TOKEN, "");
        aCache.put(Constant.USER, "");

        mUserHeadView.setImageDrawable(new IconicsDrawable(this, LbzFont.Icon.cniao_head).colorRes(R.color.white));
        mTextUserName.setText("未登录");

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Toast.makeText(MainActivity.this, "您已退出登录", Toast.LENGTH_LONG).show();
    }

    public void openAppManager(int page) {
        Intent intent = new Intent(MainActivity.this, AppManagerActivity.class);
        intent.putExtra("page", page);
        startActivity(intent);
    }
}

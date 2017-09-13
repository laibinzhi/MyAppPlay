package com.lbz.android.myappplay.ui.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.commom.util.DensityUtil;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.presenter.AppDetailPresenter;
import com.lbz.android.myappplay.ui.fragment.AppDetailFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.Bind;

public class AppDetailActivity extends BaseActivity<AppDetailPresenter> {

    @Bind(R.id.view_temp)
    View mViewTemp;

    @Bind(R.id.view_content)
    FrameLayout mViewContent;

    @Bind(R.id.view_coordinator)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.img_icon)
    ImageView mImgIcon;

    @Bind(R.id.txt_name)
    TextView mTxtName;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    private AppInfo mAppInfo;


    @Override
    protected int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        mAppInfo = (AppInfo) getIntent().getSerializableExtra("appinfo");

        ImageLoader.load(Constant.BASE_IMG_URL+mAppInfo.getIcon(),mImgIcon);
        mTxtName.setText(mAppInfo.getDisplayName());

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

        View view = mMyApplication.getView();
        Bitmap bitmap = getViewImageCache(view);

        if(bitmap!=null){
            mViewTemp.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(mViewTemp.getLayoutParams());

        marginLayoutParams.topMargin=top-DensityUtil.getStatusBarH(this);
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height =view.getHeight();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);

        mViewTemp.setLayoutParams(params);

        open();
    }

    private Bitmap getViewImageCache(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null) {
            return null;
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        view.destroyDrawingCache();
        return bitmap;
    }

    private void open() {
        int h = DensityUtil.getScreenH(this) ;

        ObjectAnimator animator = ObjectAnimator.ofFloat(mViewTemp,"scaleY",1f,(float) h);

        animator.setStartDelay(500);
        animator.setDuration(100);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

                mViewTemp.setBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                mViewTemp.setVisibility(View.GONE);
                mCoordinatorLayout.setVisibility(View.VISIBLE);

                initFragment();

            }
        });


        animator.start();

    }

    private void initFragment() {

        AppDetailFragment fragment = new AppDetailFragment(mAppInfo.getId());

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.view_content, fragment);

        transaction.commitAllowingStateLoss();

    }

}

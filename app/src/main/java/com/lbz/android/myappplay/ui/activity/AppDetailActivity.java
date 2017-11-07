package com.lbz.android.myappplay.ui.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
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
import com.lbz.android.myappplay.bean.event.AppDetailPageDownLoadBtnClickEvent;
import com.lbz.android.myappplay.bean.event.BtnIsClickEvent;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.commom.util.DensityUtil;
import com.lbz.android.myappplay.commom.util.ResourceUtil;
import com.lbz.android.myappplay.commom.util.ShareUtils;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.presenter.AppDetailPresenter;
import com.lbz.android.myappplay.ui.fragment.AppDetailFragment;
import com.lbz.android.myappplay.ui.widget.DownloadButton;
import com.lbz.android.myappplay.ui.widget.DownloadButtonConntroller;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class AppDetailActivity extends BaseActivity<AppDetailPresenter> {

    @Bind(R.id.view_temp)
    View mViewTemp;

    @Bind(R.id.view_content)
    FrameLayout mViewContent;

    @Bind(R.id.view_coordinator)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.img_icon)
    public ImageView mImgIcon;

    @Bind(R.id.txt_name)
    TextView mTxtName;

    @Bind(R.id.btn_layout)
    LinearLayout btn_layout;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Bind(R.id.download_btn)
    DownloadButton download_btn;

    @Bind(R.id.ic_share)
    ImageView mShareBtn;

    @Bind(R.id.ic_dolike)
    ImageView mDoLike;

    private AppInfo mAppInfo;
    private boolean closeAnimation;
    public boolean isFromBanner;
    private boolean onclick = false;

    DownloadButtonConntroller downloadButtonConntroller;

    @Override
    protected int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        initIsClick();

        downloadButtonConntroller = new DownloadButtonConntroller(mMyApplication);
        download_btn.setShowBorder(true);
        download_btn.setButtonRadius(30);

        mAppInfo = (AppInfo) getIntent().getSerializableExtra("appinfo");
        closeAnimation = getIntent().getBooleanExtra("closeAnimation", false);
        isFromBanner = getIntent().getBooleanExtra("isFromBanner", false);

        ImageLoader.load(Constant.BASE_IMG_URL + mAppInfo.getIcon(), mImgIcon);
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

        if (closeAnimation) {
            mViewTemp.setVisibility(View.GONE);
            mCoordinatorLayout.setVisibility(View.VISIBLE);
            btn_layout.setVisibility(View.VISIBLE);
            initFragment();
            return;
        }
        View view = mMyApplication.getView();
        Bitmap bitmap = getViewImageCache(view);

        if (bitmap != null) {
            mViewTemp.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(mViewTemp.getLayoutParams());

        marginLayoutParams.topMargin = top - DensityUtil.getStatusBarH(this);
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height = view.getHeight();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);

        mViewTemp.setLayoutParams(params);

        open();
    }

    private void initIsClick() {
        RxBus.getDefault().toObservable(BtnIsClickEvent.class)
                .subscribe(new Consumer<BtnIsClickEvent>() {
                    @Override
                    public void accept(BtnIsClickEvent event) throws Exception {
                        onclick = event.isOnClick();
                    }
                });
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
        int h = DensityUtil.getScreenH(this);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mViewTemp, "scaleY", 1f, (float) h);

        animator.setStartDelay(500);
        animator.setDuration(100);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (!isFinishing()) {

                    mViewTemp.setBackgroundColor(getResources().getColor(R.color.white));

                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (!isFinishing()) {

                    mViewTemp.setVisibility(View.GONE);

                    mCoordinatorLayout.setVisibility(View.VISIBLE);

                    btn_layout.setVisibility(View.VISIBLE);

                    initFragment();
                }
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

        downloadButtonConntroller.handClick2(download_btn, mAppInfo, getIntent().getIntExtra("position", 0));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (onclick) {
            RxBus.getDefault().post(new AppDetailPageDownLoadBtnClickEvent(null, getIntent().getIntExtra("position", 0)));
        }

    }


    @OnClick({R.id.ic_dolike, R.id.ic_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_dolike:
                break;
            case R.id.ic_share:
                openShareMenu();
                break;
        }
    }

    private void openShareMenu() {
        final String shareTextWithPlatformName = ResourceUtil.getFormattedString(
                getResources(),
                R.string.share_appino_message,
                "app_name",
                mAppInfo.getDisplayName()) + "\n" + "http://app.xiaomi.com/detail/"+mAppInfo.getId();
        ShareUtils.showShareMenu(
                ShareUtils.newShareIntent(shareTextWithPlatformName),
                mShareBtn,
                new ShareUtils.ShareMenuItemListener() {
                    @Override
                    public void onMenuItemClick(@NonNull ComponentName componentName, @NonNull ShareUtils.ShareType shareType) {
                        final String shareText;
                        if (shareType == ShareUtils.ShareType.UNKNOWN) {
                            shareText = shareTextWithPlatformName;
                        } else {
                            shareText = getSharingText(shareType);
                        }
                        final Intent intent = ShareUtils.newShareIntent(shareText);
                        intent.setComponent(componentName);
                        startActivity(intent);
                    }

                    @NonNull
                    private String getSharingText(@NonNull ShareUtils.ShareType shareType) {
                        return "";
                    }
                });
    }
}

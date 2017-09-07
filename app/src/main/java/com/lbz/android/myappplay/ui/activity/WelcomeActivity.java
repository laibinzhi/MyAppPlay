package com.lbz.android.myappplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.util.ACache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @Bind(R.id.pathView)
    PathView pathView;

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        pathView.getPathAnimator()
                .delay(100)
                .duration(2000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        jump();
                    }
                })
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void jump() {
        Log.d(TAG, "jump: ");
        if (!isDestroyed()) {

            String isShowGuide = ACache.get(this).getAsString(Constant.IS_SHOW_GUIDE);

            // 第一次启动进入引导页面
            if (null == isShowGuide) {
                startActivity(new Intent(this, GuideActivity.class));

            } else {
                startActivity(new Intent(this, MainActivity.class));
            }

            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Log.d(TAG, "onDestroy: ");
    }


}

package com.lbz.android.myappplay.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.presenter.BasePresenter;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by elitemc on 2017/7/13.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected MyApplication mMyApplication;

    @Inject
    T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        ButterKnife.bind(this);
        this.mMyApplication = (MyApplication) getApplication();
        setActivityComponent(mMyApplication.getAppComponent());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract int setLayout();

    protected abstract void setActivityComponent(AppComponent appComponent);

    protected abstract void init();
}

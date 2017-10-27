package com.lbz.android.myappplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by lbz on 2017/7/13.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment{

    private MyApplication mMyApplication;


    private View mRootView;

    @Inject
    T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayout(), container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mMyApplication = (MyApplication) getActivity().getApplication();
        setFragmentComponent(mMyApplication.getAppComponent());
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    protected abstract int setLayout();

    protected abstract void setFragmentComponent(AppComponent appComponent);

    protected abstract void init();
}

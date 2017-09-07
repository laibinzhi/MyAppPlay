package com.lbz.android.myappplay.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.lbz.android.myappplay.commom.rx.RxErrorHandle;
import com.lbz.android.myappplay.ui.BaseView;

import javax.inject.Inject;

/**
 * Created by elitemc on 2017/7/12.
 */
public class BasePresenter<M, V extends BaseView> {

    protected M mModel;

    protected V mView;

    protected Context mContext;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
        initContext();
    }

    private void initContext() {

        if (mView instanceof Fragment) {

            mContext = ((Fragment) mView).getActivity();

        } else if (mView instanceof Activity) {

            mContext = (Activity) mView;

        }

    }
}

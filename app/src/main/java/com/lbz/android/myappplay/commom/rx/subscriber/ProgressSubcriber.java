package com.lbz.android.myappplay.commom.rx.subscriber;

import android.content.Context;

import com.lbz.android.myappplay.commom.exception.BaseException;
import com.lbz.android.myappplay.commom.util.ProgressDialogHandler;
import com.lbz.android.myappplay.ui.BaseView;

/**
 * Created by elitemc on 2017/7/14.
 */
public abstract class ProgressSubcriber<T> extends ErrorHandleSubscriber<T> implements ProgressDialogHandler.OnProgressCancelListener {


    private BaseView mBaseView;

    public ProgressSubcriber(Context context, BaseView baseView) {
        super(context);
        this.mBaseView = baseView;
    }

    @Override
    public void onCancelProgress() {
        unsubscribe();
    }

    protected boolean isShowProgress() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowProgress()) {
            mBaseView.showLoading();
        }
    }

    @Override
    public void onCompleted() {
        if (isShowProgress()) {
            mBaseView.hideLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException = mRxErrorHandle.handleError(e);
        mBaseView.showError(baseException.getDisplayMessage());
    }


}

package com.lbz.android.myappplay.commom.rx.subscriber;

import android.content.Context;

import com.lbz.android.myappplay.commom.exception.BaseException;
import com.lbz.android.myappplay.commom.util.ProgressDialogHandler;
import com.lbz.android.myappplay.ui.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * Created by elitemc on 2017/7/14.
 */
public abstract class ProgressSubcriber<T> extends ErrorHandleSubscriber<T>  implements ProgressDialogHandler.OnProgressCancelListener  {


    private BaseView mBaseView;

    private Disposable mDisposable;

    public ProgressSubcriber(Context context, BaseView baseView) {
        super(context);
        this.mBaseView = baseView;
    }


    protected boolean isShowProgress() {
        return true;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if(isShowProgress()){
            mBaseView.showLoading();
        }
    }

    @Override
    public void onComplete() {
        if (isShowProgress()) {
            mBaseView.hideLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException = mRxErrorHandle.handleError(e);
        mBaseView.showError(baseException.getDisplayMessage());
    }

    @Override
    public void onCancelProgress() {
        mDisposable.dispose();
    }

}

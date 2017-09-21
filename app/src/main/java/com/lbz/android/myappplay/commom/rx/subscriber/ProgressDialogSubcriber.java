package com.lbz.android.myappplay.commom.rx.subscriber;

import android.content.Context;

import com.lbz.android.myappplay.commom.util.ProgressDialogHandler;

import io.reactivex.disposables.Disposable;

/**
 * Created by elitemc on 2017/7/14.
 */
public abstract class ProgressDialogSubcriber<T> extends ErrorHandleSubscriber<T> implements ProgressDialogHandler.OnProgressCancelListener {


    private ProgressDialogHandler mProgressDialogHandler;

    private Disposable mDisposable;

    public ProgressDialogSubcriber(Context context) {
        super(context);
        mProgressDialogHandler = new ProgressDialogHandler(context, true, this);
    }

    @Override
    public void onCancelProgress() {
        mDisposable.dispose();
    }

    protected boolean isShowProgressDialog(){
        return  true;
    }

    public void onSubscribe(Disposable d) {

        mDisposable = d;
        if(isShowProgressDialog()){
            this.mProgressDialogHandler.showProgressDialog();
        }

    }

    @Override
    public void onComplete() {
        if(isShowProgressDialog()){
            this.mProgressDialogHandler.dismissProgressDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if(isShowProgressDialog()){
            this.mProgressDialogHandler.dismissProgressDialog();
        }
    }


}

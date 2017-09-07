package com.lbz.android.myappplay.commom.rx.subscriber;

import android.content.Context;

import com.lbz.android.myappplay.commom.util.ProgressDialogHandler;

/**
 * Created by elitemc on 2017/7/14.
 */
public abstract class ProgressDialogSubcriber<T> extends ErrorHandleSubscriber<T> implements ProgressDialogHandler.OnProgressCancelListener {


    private ProgressDialogHandler mProgressDialogHandler;


    public ProgressDialogSubcriber(Context context) {
        super(context);
        mProgressDialogHandler = new ProgressDialogHandler(context, true, this);
    }

    @Override
    public void onCancelProgress() {
        unsubscribe();
    }

    protected boolean isShowProgressDialog(){
        return  true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isShowProgressDialog()){
            this.mProgressDialogHandler.showProgressDialog();
        }
    }

    @Override
    public void onCompleted() {
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

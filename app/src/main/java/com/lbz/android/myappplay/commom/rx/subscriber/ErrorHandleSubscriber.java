package com.lbz.android.myappplay.commom.rx.subscriber;

import android.content.Context;
import android.content.Intent;

import com.lbz.android.myappplay.commom.exception.BaseException;
import com.lbz.android.myappplay.commom.rx.RxErrorHandle;
import com.lbz.android.myappplay.ui.activity.LoginActivity;


/**
 * Created by elitemc on 2017/7/14.
 */
public abstract class ErrorHandleSubscriber<T> extends DefaultSubscriber<T> {

    public RxErrorHandle mRxErrorHandle;

    protected Context mContext;


    public ErrorHandleSubscriber(Context context) {
        this.mContext = context;
        mRxErrorHandle = new RxErrorHandle(context);
    }

    @Override
    public void onError(Throwable e) {

        BaseException baseException = mRxErrorHandle.handleError(e);

        mRxErrorHandle.showErrorMessage(baseException);
        if (baseException.getCode() == BaseException.ERROR_TOKEN) {
            toLogin();
        }
    }

    private void toLogin() {

        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

}

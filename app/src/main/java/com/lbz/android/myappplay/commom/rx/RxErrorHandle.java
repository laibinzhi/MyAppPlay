package com.lbz.android.myappplay.commom.rx;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.lbz.android.myappplay.commom.exception.ApiException;
import com.lbz.android.myappplay.commom.exception.BaseException;
import com.lbz.android.myappplay.commom.exception.ErrorMessageFactory;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.exceptions.CompositeException;
import retrofit2.HttpException;

/**
 * Created by lbz on 2017/7/14.
 */
public class RxErrorHandle {

    private Context mContext;

    public RxErrorHandle(Context mContext) {
        this.mContext = mContext;
    }

    public BaseException handleError(Throwable e) {

        e.printStackTrace();

        Log.e("errortag","e="+e.toString());

        BaseException exception = new BaseException();

        if (e instanceof ApiException) {

            exception.setCode(((ApiException) e).getCode());

        } else if (e instanceof JsonParseException) {

            exception.setCode(BaseException.JSON_ERROR);

        } else if (e instanceof HttpException) {

            exception.setCode(((HttpException) e).code());

        } else if (e instanceof SocketTimeoutException) {

            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);

        } else if (e instanceof SocketException) {

            exception.setCode(BaseException.SOCKET_ERROR);

        }else if (e instanceof UnknownHostException){

            exception.setCode(BaseException.UNKOWNHOST_ERROR);

        }
        else if (e instanceof CompositeException){

            exception.setCode(BaseException.UNKOWNHOST_ERROR);

        }
        else {

            exception.setCode(BaseException.UNKNOWN_ERROR);

        }

        exception.setDisplayMessage(ErrorMessageFactory.create(mContext, exception.getCode()));

        return exception;

    }

    public void showErrorMessage(BaseException e) {

//        Toast.makeText(mContext, e.getDisplayMessage(), Toast.LENGTH_LONG).show();

    }

}

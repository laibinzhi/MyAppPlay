package com.lbz.android.myappplay.commom.exception;

/**
 * Created by elitemc on 2017/7/14.
 */
public class ApiException extends BaseException {

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }

}

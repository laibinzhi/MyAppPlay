package com.lbz.android.myappplay.bean;

import java.io.Serializable;

/**
 * Created by elitemc on 2017/7/14.
 */
public class BaseHttpResultBean<T> implements Serializable {

    public static final int SUCCESS = 1;


    private int status;

    private String message;


    private T data;

    public int getCode() {
        return status;
    }

    public void setCode(int code) {
        this.status = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return (status == SUCCESS);
    }
}

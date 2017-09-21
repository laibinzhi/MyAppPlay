package com.lbz.android.myappplay.commom.util;

import android.app.Activity;
import android.content.Context;


import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;


public class PermissionUtil {


    public static Observable<Boolean> requestPermisson(Context activity, String permission){


        RxPermissions rxPermissions =  new RxPermissions((Activity) activity);

        return rxPermissions.request(permission);

    }

}

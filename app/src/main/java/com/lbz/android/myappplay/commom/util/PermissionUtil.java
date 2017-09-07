package com.lbz.android.myappplay.commom.util;

import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.Subscriber;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.common.util
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class PermissionUtil {




    public static void readPhonestate(Activity activity){



        requestPermisson(activity, Manifest.permission.READ_PHONE_STATE).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }
        });

//        RxPermissions rxPermissions = new RxPermissions(activity);
//        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Subscriber<Boolean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//
//                    }
//                });

    }


    public static Observable<Boolean> requestPermisson(Activity activity, String permission){


        RxPermissions rxPermissions = new RxPermissions(activity);


        return rxPermissions.request(permission);
    }

    public static Observable.Transformer<Object, Boolean> ensure(Activity activity, String permission){

        RxPermissions rxPermissions = new RxPermissions(activity);

       return  rxPermissions.ensure(permission);

    }



}

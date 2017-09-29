package com.lbz.android.myappplay.commom.util;

import android.content.Context;

import com.lbz.android.myappplay.commom.Constant;

import java.util.HashMap;

/**
 * Created by elitemc on 2017/9/29.
 */

public class HttpPostRequestMap {

    private volatile static HttpPostRequestMap singleton;

    private HttpPostRequestMap(){}

    public static HttpPostRequestMap getSingleton() {
             if (singleton == null) {
                     synchronized (HttpPostRequestMap.class) {
                         if (singleton == null) {
                                 singleton = new HttpPostRequestMap();
                             }
                         }
                 }
             return singleton;
    }

    public HashMap<String,String> getHttpPostRequestMap(Context context){
        HashMap<String, String> fields = new HashMap<>();
        fields.put(Constant.CHANNEL, "market_100_1_android");
        fields.put(Constant.CLIENT_ID, "be8597169281e5379632c34ab20ed13c");
        fields.put(Constant.CO, "CN");
        fields.put(Constant.DENSITY_SCALE_FACTOR, context.getResources().getDisplayMetrics().density + "");
        fields.put(Constant.IMEI, "48667b0737e9817b56728704448d0729");
        fields.put(Constant.LANGUAGE, DeviceUtils.getLanguage());
        fields.put(Constant.MARKET_VERSION, "147");
        fields.put(Constant.MODEL, DeviceUtils.getModel());
        fields.put(Constant.os, DeviceUtils.getBuildVersionIncremental());
        fields.put(Constant.RESOLUTION, DensityUtil.getScreenW(context) + "*" + DensityUtil.getScreenH(context));
        fields.put(Constant.SDK, DeviceUtils.getBuildVersionSDK() + "");
        fields.put(Constant.STAMP, 0 + "");
        return fields;
    }

}

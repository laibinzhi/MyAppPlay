package com.lbz.android.myappplay.commom.apkparset;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class AndroidApkParser {


    public static Resources getResources(Context context, String apkPath) throws Exception {
        String PATH_AssetManager = "android.content.res.AssetManager";
        Class assetMagCls = Class.forName(PATH_AssetManager);
        Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
        Object assetMag = assetMagCt.newInstance((Object[]) null);
        Class[] typeArgs = new Class[1];
        typeArgs[0] = String.class;
        Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",
                typeArgs);
        Object[] valueArgs = new Object[1];
        valueArgs[0] = apkPath;
        assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
        Resources res = context.getResources();
        typeArgs = new Class[3];
        typeArgs[0] = assetMag.getClass();
        typeArgs[1] = res.getDisplayMetrics().getClass();
        typeArgs[2] = res.getConfiguration().getClass();
        Constructor resCt = Resources.class.getConstructor(typeArgs);
        valueArgs = new Object[3];
        valueArgs[0] = assetMag;
        valueArgs[1] = res.getDisplayMetrics();
        valueArgs[2] = res.getConfiguration();
        res = (Resources) resCt.newInstance(valueArgs);
        return res;
    }


    public static AndroidApk getUninstallAPK(Context context, String path) {

        AndroidApk apk = null;


        PackageManager pm = context.getPackageManager();

        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            apk = new AndroidApk();
            apk.setPackageName(info.packageName);
            apk.setApkPath(path);
            apk.setAppVersionCode(info.versionCode + "");
            apk.setAppVersionName(info.versionName);

            Resources res = null;

            try {
                res = getResources(context, path);

                apk.setAppName(res.getString(info.applicationInfo.labelRes));

                Drawable icon = res.getDrawable(info.applicationInfo.icon);
                apk.setDrawable(icon);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return apk;
    }
}

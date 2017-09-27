package com.lbz.android.myappplay.commom.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lbz.android.myappplay.bean.event.AppInstallEvent;
import com.lbz.android.myappplay.commom.rx.RxBus;

/**
 * Created by elitemc on 2017/9/27.
 */

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInstallEvent(Intent.ACTION_PACKAGE_ADDED, packageName));
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInstallEvent(Intent.ACTION_PACKAGE_REMOVED, packageName));

        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            RxBus.getDefault().post(new AppInstallEvent(Intent.ACTION_PACKAGE_REPLACED, packageName));
        }

    }
}

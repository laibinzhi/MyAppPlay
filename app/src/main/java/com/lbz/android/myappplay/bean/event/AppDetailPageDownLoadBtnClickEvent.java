package com.lbz.android.myappplay.bean.event;

import com.lbz.android.myappplay.bean.AppInfo;

/**
 * Created by elitemc on 2017/9/27.
 */

public class AppDetailPageDownLoadBtnClickEvent {

    private AppInfo mAppInfo;
    private int position;

    public AppDetailPageDownLoadBtnClickEvent(AppInfo appInfo, int position) {
        this.mAppInfo = appInfo;
        this.position = position;
    }

    public AppInfo getmAppInfo() {
        return mAppInfo;
    }

    public void setmAppInfo(AppInfo mAppInfo) {
        this.mAppInfo = mAppInfo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}

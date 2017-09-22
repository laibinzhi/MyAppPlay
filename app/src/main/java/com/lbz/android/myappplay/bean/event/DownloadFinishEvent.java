package com.lbz.android.myappplay.bean.event;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.bean.AppInfo;

/**
 * Created by elitemc on 2017/9/22.
 */

public class DownloadFinishEvent {

    private BaseViewHolder holder;
    private AppInfo appInfo;

    public DownloadFinishEvent(BaseViewHolder holder, AppInfo appInfo) {
        this.holder = holder;
        this.appInfo = appInfo;
    }

    public BaseViewHolder getHolder() {
        return holder;
    }

    public void setHolder(BaseViewHolder holder) {
        this.holder = holder;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}

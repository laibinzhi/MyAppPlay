package com.lbz.android.myappplay.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elitemc on 2017/9/7.
 */

public class PageMiBean implements Serializable {

    private boolean hasMore;
    private String host;
    private String sid;
    private String thumbnail;
    private List<AppMiBean> listApp;
    private List<Category> categories;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<AppMiBean> getListApp() {
        return listApp;
    }

    public void setListApp(List<AppMiBean> listApp) {
        this.listApp = listApp;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

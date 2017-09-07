package com.lbz.android.myappplay.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elitemc on 2017/9/7.
 */

public class PageBean implements Serializable {

    private boolean hasMore;
    private String host;
    private String sid;
    private String thumbnail;
    private List<AppInfo> listApp;
    private List<AppInfo> listExtrasApp;
    private List<AppInfo> listExtrasGameApp;
    private List<Category> categories;
    private List<ThemeBean> topfeaturedList;
    private List<ThemeBean> topTheme;
    private List<ThemeBean> bottomTheme;

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

    public List<AppInfo> getListApp() {
        return listApp;
    }

    public void setListApp(List<AppInfo> listApp) {
        this.listApp = listApp;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<AppInfo> getListExtrasApp() {
        return listExtrasApp;
    }

    public void setListExtrasApp(List<AppInfo> listExtrasApp) {
        this.listExtrasApp = listExtrasApp;
    }

    public List<AppInfo> getListExtrasGameApp() {
        return listExtrasGameApp;
    }

    public void setListExtrasGameApp(List<AppInfo> listExtrasGameApp) {
        this.listExtrasGameApp = listExtrasGameApp;
    }

    public List<ThemeBean> getTopfeaturedList() {
        return topfeaturedList;
    }

    public void setTopfeaturedList(List<ThemeBean> topfeaturedList) {
        this.topfeaturedList = topfeaturedList;
    }

    public List<ThemeBean> getTopTheme() {
        return topTheme;
    }

    public void setTopTheme(List<ThemeBean> topTheme) {
        this.topTheme = topTheme;
    }

    public List<ThemeBean> getBottomTheme() {
        return bottomTheme;
    }

    public void setBottomTheme(List<ThemeBean> bottomTheme) {
        this.bottomTheme = bottomTheme;
    }
}

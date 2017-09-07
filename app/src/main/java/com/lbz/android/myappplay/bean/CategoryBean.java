package com.lbz.android.myappplay.bean;

import java.util.List;

/**
 * Created by elitemc on 2017/9/6.
 */

public class CategoryBean {

    private String host;

    private String thumbnail;


    private List<Category> categories;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

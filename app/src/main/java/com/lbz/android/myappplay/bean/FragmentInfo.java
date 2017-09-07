package com.lbz.android.myappplay.bean;


import java.io.Serializable;

/**
 * Created by elitemc on 2017/7/11.
 */
public class FragmentInfo implements Serializable{


    private String title;
    private Class fragment;

    public FragmentInfo(String title, Class fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}

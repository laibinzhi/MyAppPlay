package com.lbz.android.myappplay.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elitemc on 2017/9/7.
 */

public class SubjectBean implements Serializable {

    /**
     * adType : 0
     * ads : 0
     * adsType : 0
     * displayType : 0
     * external : false
     * featuredType : 2
     * id : 4125
     * listApp : []
     * mtDisplayEffect : 0
     * mtPosition : 2
     * mticon : AppStore/02bf54de8ab5fa8be7b6176d8edda1d8a9c401841
     * mticonHigh : 0
     * mticonWidth : 0
     * position : 0
     * priority : 2
     * rId : 0
     * relatedId : 167924
     * title : 专题合辑
     * uid : 4b445
     * updateTime : 0
     * webViewPicHeight : 0
     * webViewPicWidth : 0
     */

    private int adType;
    private int ads;
    private int adsType;
    private int displayType;
    private boolean external;
    private int featuredType;
    private int id;
    private int mtDisplayEffect;
    private int mtPosition;
    private String mticon;
    private int mticonHigh;
    private int mticonWidth;
    private int position;
    private int priority;
    private int rId;
    private int relatedId;
    private String title;
    private String uid;
    private int updateTime;
    private int webViewPicHeight;
    private int webViewPicWidth;
    private List<?> listApp;
    private String h5BigIcon;

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public int getAds() {
        return ads;
    }

    public void setAds(int ads) {
        this.ads = ads;
    }

    public int getAdsType() {
        return adsType;
    }

    public void setAdsType(int adsType) {
        this.adsType = adsType;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public int getFeaturedType() {
        return featuredType;
    }

    public void setFeaturedType(int featuredType) {
        this.featuredType = featuredType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMtDisplayEffect() {
        return mtDisplayEffect;
    }

    public void setMtDisplayEffect(int mtDisplayEffect) {
        this.mtDisplayEffect = mtDisplayEffect;
    }

    public int getMtPosition() {
        return mtPosition;
    }

    public void setMtPosition(int mtPosition) {
        this.mtPosition = mtPosition;
    }

    public String getMticon() {
        return mticon;
    }

    public void setMticon(String mticon) {
        this.mticon = mticon;
    }

    public int getMticonHigh() {
        return mticonHigh;
    }

    public void setMticonHigh(int mticonHigh) {
        this.mticonHigh = mticonHigh;
    }

    public int getMticonWidth() {
        return mticonWidth;
    }

    public void setMticonWidth(int mticonWidth) {
        this.mticonWidth = mticonWidth;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRId() {
        return rId;
    }

    public void setRId(int rId) {
        this.rId = rId;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getWebViewPicHeight() {
        return webViewPicHeight;
    }

    public void setWebViewPicHeight(int webViewPicHeight) {
        this.webViewPicHeight = webViewPicHeight;
    }

    public int getWebViewPicWidth() {
        return webViewPicWidth;
    }

    public void setWebViewPicWidth(int webViewPicWidth) {
        this.webViewPicWidth = webViewPicWidth;
    }

    public List<?> getListApp() {
        return listApp;
    }

    public void setListApp(List<?> listApp) {
        this.listApp = listApp;
    }

    public String getH5BigIcon() {
        return h5BigIcon;
    }

    public void setH5BigIcon(String h5BigIcon) {
        this.h5BigIcon = h5BigIcon;
    }
}

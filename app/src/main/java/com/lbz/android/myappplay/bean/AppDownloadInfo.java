package com.lbz.android.myappplay.bean;

import java.io.Serializable;

import static com.lbz.android.myappplay.commom.Constant.BASE_DOWNLOAD_URL;

/**
 * Created by elitemc on 2017/9/21.
 */

public class AppDownloadInfo implements Serializable {

    /**
     * apk : AppStore/02f264db6001086176ae81fa6c9342e6f1b41450d
     * apkHash : b213283960ede2def0d64e6e221d919c
     * apkSize : 38306534
     * appendExpansionPackSize : 0
     * channelApkId : -1
     * diffFileSize : 0
     * fitness : 0
     * gamePackSize : 0
     * hdIcon : {"main":"AppStore/00ed1b5551e094e043c34255aa1621513b4f07014"}
     * host : http://f7.market.xiaomi.com/download/
     * icon : AppStore/01ba547606306f5a262b5f836c295dcfd2e4291d1
     * id : 69662
     * mainExpansionPackSize : 0
     * refPosition : -1
     * releaseKeyHash : ed8fb51bcd8741347edfcbf1305ce6ef
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     * unfitnessType : 0
     */

    private String apk;
    private String apkHash;
    private int apkSize;
    private int appendExpansionPackSize;
    private int channelApkId;
    private int diffFileSize;
    private int fitness;
    private int gamePackSize;
    private HdIconBean hdIcon;
    private String host;
    private String icon;
    private int id;
    private int mainExpansionPackSize;
    private int refPosition;
    private String releaseKeyHash;
    private String thumbnail;
    private int unfitnessType;
    private String dowanloadUrl;

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public String getApkHash() {
        return apkHash;
    }

    public void setApkHash(String apkHash) {
        this.apkHash = apkHash;
    }

    public int getApkSize() {
        return apkSize;
    }

    public void setApkSize(int apkSize) {
        this.apkSize = apkSize;
    }

    public int getAppendExpansionPackSize() {
        return appendExpansionPackSize;
    }

    public void setAppendExpansionPackSize(int appendExpansionPackSize) {
        this.appendExpansionPackSize = appendExpansionPackSize;
    }

    public int getChannelApkId() {
        return channelApkId;
    }

    public void setChannelApkId(int channelApkId) {
        this.channelApkId = channelApkId;
    }

    public int getDiffFileSize() {
        return diffFileSize;
    }

    public void setDiffFileSize(int diffFileSize) {
        this.diffFileSize = diffFileSize;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getGamePackSize() {
        return gamePackSize;
    }

    public void setGamePackSize(int gamePackSize) {
        this.gamePackSize = gamePackSize;
    }

    public HdIconBean getHdIcon() {
        return hdIcon;
    }

    public void setHdIcon(HdIconBean hdIcon) {
        this.hdIcon = hdIcon;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainExpansionPackSize() {
        return mainExpansionPackSize;
    }

    public void setMainExpansionPackSize(int mainExpansionPackSize) {
        this.mainExpansionPackSize = mainExpansionPackSize;
    }

    public int getRefPosition() {
        return refPosition;
    }

    public void setRefPosition(int refPosition) {
        this.refPosition = refPosition;
    }

    public String getReleaseKeyHash() {
        return releaseKeyHash;
    }

    public void setReleaseKeyHash(String releaseKeyHash) {
        this.releaseKeyHash = releaseKeyHash;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getUnfitnessType() {
        return unfitnessType;
    }

    public void setUnfitnessType(int unfitnessType) {
        this.unfitnessType = unfitnessType;
    }

    public String getDownloadUrl(){
        return  BASE_DOWNLOAD_URL+ this.getApk();
    }

    public void setDowanloadUrl(String dowanloadUrl) {
        this.dowanloadUrl = dowanloadUrl;
    }

    public static class HdIconBean implements Serializable{
        /**
         * main : AppStore/00ed1b5551e094e043c34255aa1621513b4f07014
         */

        private String main;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }
}

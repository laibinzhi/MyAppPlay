package com.lbz.android.myappplay.bean;

/**
 * Created by lbz on 2017/9/21.
 */

public class DownloadFlag extends zlc.season.rxdownload2.entity.DownloadFlag {

    public static final int UN_INSTALL = 10000;     //未安装
    public static final int FILE_EXIST = 10001;     // 文件存在
    public static final int SHOULD_UPDATE = 10002;     // 需要升级

}

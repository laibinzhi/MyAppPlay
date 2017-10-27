package com.lbz.android.myappplay.bean.event;

/**
 * Created by lbz on 2017/9/27.
 */

public class AppInstallEvent {

    private String FLAG;
    private String pgName;

    public AppInstallEvent(String FLAG, String pgName) {
        this.FLAG = FLAG;
        this.pgName = pgName;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

}

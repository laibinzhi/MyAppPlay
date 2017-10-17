package com.lbz.android.myappplay.bean.event;

/**
 * Created by elitemc on 2017/10/17.
 */

public class BtnIsClickEvent {

    private boolean onClick;

    private int position;

    public BtnIsClickEvent(boolean onClick, int position) {
        this.onClick = onClick;
        this.position = position;
    }

    public boolean isOnClick() {
        return onClick;
    }

    public void setOnClick(boolean onClick) {
        this.onClick = onClick;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

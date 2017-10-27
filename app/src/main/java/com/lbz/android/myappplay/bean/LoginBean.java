package com.lbz.android.myappplay.bean;

import java.io.Serializable;

/**
 * Created by lbz on 2017/7/11.
 */

public class LoginBean  implements Serializable{

    private String token;

    private  User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.lbz.android.myappplay.bean;

import java.io.Serializable;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.bean
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
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

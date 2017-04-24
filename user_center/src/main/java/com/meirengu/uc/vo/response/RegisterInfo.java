package com.meirengu.uc.vo.response;

import com.meirengu.uc.model.User;

/**
 * Created by huoyan403 on 3/14/2017.
 */
public class RegisterInfo {

    public User user;
    public String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

package com.meirengu.commons.authority.model;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

public class ARPEntity extends BaseObject {

    private static final long serialVersionUID = 1L;
    private User user;
    private SimpleAuthorizationInfo info;

    public User getUser() {
        return user;
    }

    public void setAccount(User user) {
        this.user = user;
    }

    public SimpleAuthorizationInfo getInfo() {
        return info;
    }

    public void setInfo(SimpleAuthorizationInfo info) {
        this.info = info;
    }
}

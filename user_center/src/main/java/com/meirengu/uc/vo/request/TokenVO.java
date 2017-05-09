package com.meirengu.uc.vo.request;

import com.meirengu.model.BaseObject;

/**
 * Created by huoyan403 on 5/9/2017.
 */
public class TokenVO extends BaseObject {

    private String token;
    private String deviceId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

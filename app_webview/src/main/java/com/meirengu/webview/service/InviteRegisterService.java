package com.meirengu.webview.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 邀请注册接口类
 *
 * @author Marvin
 * @create 2017-03-31 下午5:48
 */
public interface InviteRegisterService {

    /**
     * 邀请注册
     * @param registerPhone
     * @param inviterPhone
     * @param from
     * @param ip
     * @return
     */
    public JSONObject inviteRegister(String registerPhone, String inviterPhone, String from, String ip);
}

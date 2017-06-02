package com.meirengu.webview.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户模块服务层
 * @author 建新
 * @create 2017-05-31 11:08
 */
public interface UserCenterService {

    /**
     * 获取验证码
     * @param mobile
     * @param ip
     * @param type
     * @return
     */
    JSONObject getCheckCode(String mobile, String ip, String type);

    /**
     * 获取邀请记录
     * @param userId
     * @param page
     * @param perPage
     * @return
     */
    JSONObject getInviteRecord(String userId, Integer page, Integer perPage);

    /**
     * 注册
     * @param mobile
     * @param checkCode
     * @param mobileInviter
     * @param from
     * @param ip
     * @return
     */
    JSONObject register(String mobile, String checkCode, String mobileInviter, int from, String ip);
}

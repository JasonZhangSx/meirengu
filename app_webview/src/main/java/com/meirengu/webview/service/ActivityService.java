package com.meirengu.webview.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 活动接口类
 *
 * @author Marvin
 * @create 2017-03-30 下午5:50
 */
public interface ActivityService {

    /**
     * 获取活动列表
     * @return
     */
    public JSONObject activities();

}

package com.meirengu.webview.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 报名活动
 * @author 建新
 * @create 2017-05-24 16:41
 */
public interface SignupService {

    JSONObject signup(String name, String telphone, Integer type, String city);
}

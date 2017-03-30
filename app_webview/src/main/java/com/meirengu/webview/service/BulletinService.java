package com.meirengu.webview.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 平台公告接口类
 *
 * @author Marvin
 * @create 2017-03-30 下午7:19
 */
public interface BulletinService {

    /**
     * 获取平台公告
     * @return
     */
    public JSONObject bulletins();

}

package com.meirengu.news.service;

import java.util.Map;

/**
 * 系统服务
 * @author 建新
 * @create 2017-04-06 16:49
 */
public interface SysService {

    Map<String, Object> initApp(Integer appId, Integer appChannel, Integer status);
}

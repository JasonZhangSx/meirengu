package com.meirengu.erp.service;

import com.meirengu.erp.model.VersionUpgrade;

import java.util.Map;

/**
 * 版本管理
 *
 * @author 建新
 * @create 2017-05-17 17:38
 */
public interface SignupActivityService {

    Map<String, Object> query(int page, int perPage, boolean isPage, Integer type, String name, String telphone, String city);

}

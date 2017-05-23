package com.meirengu.erp.service;

import com.meirengu.erp.model.VersionUpgrade;
import com.meirengu.utils.ObjectUtils;

import java.util.Map;

/**
 * 版本管理
 *
 * @author 建新
 * @create 2017-05-17 17:38
 */
public interface VersionService {

    Map<String, Object> query(int page, int perPage, boolean isPage, int appChannel, int appId, String versionCode, int status);

    Map<String, Object> detail(int id);

    Map<String, Object> add(VersionUpgrade versionUpgrade);

    Map<String, Object> update(VersionUpgrade versionUpgrade);

    Map<String, Object> delete(int id);
}

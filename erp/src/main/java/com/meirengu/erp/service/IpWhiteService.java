package com.meirengu.erp.service;

import com.meirengu.erp.model.Article;
import com.meirengu.erp.model.IpWhiteList;

import java.util.Map;

/**
 * 白名单service
 * @author 建新
 * @create 2017-07-24 11:48
 */
public interface IpWhiteService {

    Map<String, Object> query(int page, int perPage, boolean isPage, Integer status);

    Map<String, Object> detail(int id);

    Map<String, Object> add(IpWhiteList ipWhiteList);

    Map<String, Object> update(IpWhiteList ipWhiteList);

    Map<String, Object> setCache();
}

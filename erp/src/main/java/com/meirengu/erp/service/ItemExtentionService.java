package com.meirengu.erp.service;

import com.meirengu.erp.model.ItemExtention;
import com.meirengu.erp.model.Partner;

import java.util.Map;

/**
 * 项目扩展service
 * @author 建新
 * @create 2017-06-08 12:05
 */
public interface ItemExtentionService {

    Object query(int page, int perPage, boolean isPage);

    Map<String, Object> detail(int id);

    Map<String, Object> add(ItemExtention extention);

    Map<String, Object> update(ItemExtention extention);

    Map<String, Object> delete(int id);
}

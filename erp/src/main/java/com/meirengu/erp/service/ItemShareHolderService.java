package com.meirengu.erp.service;

import com.meirengu.erp.model.ItemShareholder;

import java.util.Map;

/**
 * 项目股东service
 * @author 建新
 * @create 2017-06-08 12:05
 */
public interface ItemShareHolderService {

    Object query(int page, int perPage, boolean isPage, Integer itemId);

    Map<String, Object> detail(int id);

    Map<String, Object> add(ItemShareholder shareholder);

    Map<String, Object> update(ItemShareholder shareHolder);

    Map<String, Object> delete(int id);
}

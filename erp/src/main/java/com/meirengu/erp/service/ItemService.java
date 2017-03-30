package com.meirengu.erp.service;

import com.meirengu.erp.model.Item;

import java.util.List;
import java.util.Map;

/**
 * 项目服务
 * @author 建新
 * @create 2017-03-29 19:31
 */
public interface ItemService {

    Map<String, Object> getItemListByPage(boolean isPage, Integer itemId, String itemName, String itemStatus);

    boolean itemAdd(Map<String, String> params);

    List getItemClassList(Map<String, String> params);


}

package com.meirengu.erp.service;

import com.meirengu.erp.model.Item;
import com.meirengu.erp.model.ItemContent;
import com.meirengu.erp.model.ItemLevel;

import java.util.List;
import java.util.Map;

/**
 * 项目服务
 * @author 建新
 * @create 2017-03-29 19:31
 */
public interface ItemService {

    /**
     * 分页获取项目列表
     * @param isPage
     * @param itemId
     * @param itemName
     * @param itemStatus
     * @return
     */
    Map<String, Object> getItemListByPage(boolean isPage, Integer itemId, String itemName, String itemStatus);

    /**
     * 项目添加
     * @param params
     * @return
     */
    Object itemInsert(Map<String, String> params);

    /**
     * 获取项目分类列表
     * @param params
     * @return
     */
    List getItemClassList(Map<String, String> params);

    /**
     * 项目内容添加
     * @param params
     * @return
     */
    Object contentInsert(Map<String, String> params);

    /**
     * 项目回报添加
     * @param params
     * @return
     */
    Object levelInsert(Map<String, String> params);

    /**
     * 项目修改
     * @param params
     * @return
     */
    boolean itemUpdate(Map<String, String> params);

    /**
     * 项目内容修改
     * @param params
     * @return
     */
    boolean contentUpdate(Map<String, String> params);

    /**
     * 项目档位修改
     * @param params
     * @return
     */
    boolean levelUpdate(Map<String, String> params);

    /**
     * 项目提交初审
     * @param itemId
     * @return
     */
    boolean verify(Integer itemId);
}
package com.meirengu.webview.service;

import java.util.List;
import java.util.Map;

/**
 * 项目service
 * @author 建新
 * @create 2017-05-09 19:11
 */
public interface ItemService {
    /**
     * 获取项目详情
     * @param itemId
     * @return
     */
    Map<String, Object> getItemDetail(Integer itemId);

    /**
     * 获取档位列表
     * @param itemId
     * @return
     */
    List<Map<String, Object>> getLevelList(Integer itemId);

    /**
     * 获取内容列表
     * @param itemId
     * @return
     */
    List<Map<String, Object>> getContentList(Integer itemId);
}

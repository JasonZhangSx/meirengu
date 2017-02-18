package com.meirengu.medical.service;

import com.meirengu.medical.vo.ItemVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/12 11:58.
 * 项目业务逻辑接口
 */
public interface IItemService {

    /**
     * 添加项目数据
     * @param itemVo 项目Model
     * @return 添加结果
     */
    String addItemData(ItemVo itemVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest);

    /**
     * 获取项目数据
     * @param itemVo 项目Model
     * @return 查询结果
     */
    String getItemData(ItemVo itemVo);

    /**
     * 获取项目案例
     * @param itemVo 项目Model
     * @return 查询结果
     */
    List getRelationCase(ItemVo itemVo);

    /**
     * 更新项目数据
     * @param itemVo 项目Model
     * @return 更新结果
     */
    String updateItemData(ItemVo itemVo);

    /**
     * 删除项目数据
     * @param itemId 项目Id
     * @return 删除结果
     */
    String deleteItemData(Integer itemId);

    /**
     * 获取服务项目
     * @param itemVo 项目Model
     * @return 查询结果
     */
    String getServiceItem(ItemVo itemVo);

    /**
     * 获取项目详情
     * @param itemVo 项目Model
     * @return 查询结果
     */
    String getItemDetail(ItemVo itemVo);
}

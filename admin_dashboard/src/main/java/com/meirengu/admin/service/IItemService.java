package com.meirengu.admin.service;

import com.meirengu.admin.vo.ItemVo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    Map addItemData(ItemVo itemVo, Iterator<String> iter, MultipartHttpServletRequest multipartHttpServletRequest);

    /**
     * 获取项目数据
     * @param itemVo 项目Model
     * @return 查询结果
     */
    Map getItemData(ItemVo itemVo);

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
    Map updateItemData(ItemVo itemVo);

    /**
     * 删除项目数据
     * @param itemId 项目Id
     * @return 删除结果
     */
    Map deleteItemData(Integer itemId);

    /**
     * 获取服务项目
     * @param itemVo 项目Model
     * @return 查询结果
     */
    Map getServiceItem(ItemVo itemVo);

    /**
     * 获取项目详情
     * @param itemVo 项目Model
     * @return 查询结果
     */
    Map getItemDetail(ItemVo itemVo);


    /**
     * 添加项目所需数据
     * @return
     */
    Map addItem();

}

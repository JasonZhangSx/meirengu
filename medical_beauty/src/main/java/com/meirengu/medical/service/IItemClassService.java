package com.meirengu.medical.service;

import com.meirengu.medical.vo.ItemClassVo;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 11:28.
 * 项目分类业务逻辑接口
 */
public interface IItemClassService {

    /**
     * 条件添加项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    String addItemClassData(ItemClassVo itemClassVo);

    /**
     *
     * 条件查询项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    String getItemClassData(ItemClassVo itemClassVo);

    /**
     * 条件更新项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    String updateItemClassData(ItemClassVo itemClassVo);

    /**
     * 条件删除项目分类
     * @param icId 项目分类Id
     * @return 将添加结果返回
     */
    String deleteItemClassData(Integer icId);
}

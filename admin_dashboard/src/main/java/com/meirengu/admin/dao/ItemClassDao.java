package com.meirengu.admin.dao;


import com.meirengu.admin.vo.ItemClassVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemClassDao {

    /**
     * 条件添加项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    int addItemClassData(ItemClassVo itemClassVo);

    /**
     * 条件查询项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    List<ItemClassVo> getItemClassData(ItemClassVo itemClassVo);

    /**
     * 条件更新项目分类
     * @param itemClassVo 项目分类Model
     * @return 将添加结果返回
     */
    int updateItemClassData(ItemClassVo itemClassVo);

}
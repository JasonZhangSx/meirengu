package com.meirengu.admin.dao;


import com.meirengu.admin.vo.ItemVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao {
    /**
     * 获取服务项目
     * @param itemVo 项目Model
     * @return 查询结果
     */
    List getServiceItem(ItemVo itemVo);

    /**
     * 获取项目详情
     * @param itemVo 项目Model
     * @return 查询结果
     */
    List getItemDetail(ItemVo itemVo);

    /**
     * 获取项目案例
     * @param itemVo 项目Model
     * @return 查询结果
     */
    List getRelationCase(ItemVo itemVo);

    /**
     * 添加项目数据
     * @param itemVo 项目Model
     * @return 添加结果
     */
    int addItemData(ItemVo itemVo);

    /**
     * 获取项目数据
     * @param itemVo 项目Model
     * @return 查询结果
     */
    List getItemData(ItemVo itemVo);

    /**
     * 更新项目数据
     * @param itemVo 项目Model
     * @return 更新结果
     */
    int updateItemData(ItemVo itemVo);
}
package com.meirengu.medical.dao;


import com.meirengu.medical.vo.ItemVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao {

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
    List<ItemVo> getItemData(ItemVo itemVo);

    /**
     * 更新项目数据
     * @param itemVo 项目Model
     * @return 更新结果
     */
    int updateItemData(ItemVo itemVo);
}
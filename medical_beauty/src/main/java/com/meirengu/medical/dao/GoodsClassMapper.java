package com.meirengu.medical.dao;

import com.meirengu.medical.model.GoodsClass;

public interface GoodsClassMapper {
    int deleteByPrimaryKey(Integer gcId);

    int insert(GoodsClass record);

    int insertSelective(GoodsClass record);

    GoodsClass selectByPrimaryKey(Integer gcId);

    int updateByPrimaryKeySelective(GoodsClass record);

    int updateByPrimaryKey(GoodsClass record);
}
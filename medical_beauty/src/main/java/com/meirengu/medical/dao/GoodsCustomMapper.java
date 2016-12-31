package com.meirengu.medical.dao;

import com.meirengu.medical.model.GoodsCustom;

public interface GoodsCustomMapper {
    int deleteByPrimaryKey(Integer customId);

    int insert(GoodsCustom record);

    int insertSelective(GoodsCustom record);

    GoodsCustom selectByPrimaryKey(Integer customId);

    int updateByPrimaryKeySelective(GoodsCustom record);

    int updateByPrimaryKey(GoodsCustom record);
}
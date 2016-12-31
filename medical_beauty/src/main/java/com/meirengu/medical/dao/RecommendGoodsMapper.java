package com.meirengu.medical.dao;

import com.meirengu.medical.model.RecommendGoods;

public interface RecommendGoodsMapper {

    int insert(RecommendGoods record);

    int insertSelective(RecommendGoods record);

    int updateByPrimaryKeySelective(RecommendGoods record);

    int updateByPrimaryKey(RecommendGoods record);
}
package com.meirengu.medical.dao;

import com.meirengu.medical.model.Recommend;

public interface RecommendMapper {
    int deleteByPrimaryKey(Integer recommendId);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    Recommend selectByPrimaryKey(Integer recommendId);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);
}
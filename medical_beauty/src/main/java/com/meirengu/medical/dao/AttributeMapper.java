package com.meirengu.medical.dao;

import com.meirengu.medical.model.Attribute;

public interface AttributeMapper {
    int deleteByPrimaryKey(Integer attrId);

    int insert(Attribute record);

    int insertSelective(Attribute record);

    Attribute selectByPrimaryKey(Integer attrId);

    int updateByPrimaryKeySelective(Attribute record);

    int updateByPrimaryKeyWithBLOBs(Attribute record);

    int updateByPrimaryKey(Attribute record);
}
package com.meirengu.medical.dao;

import com.meirengu.medical.model.Spec;

public interface SpecMapper {
    int deleteByPrimaryKey(Integer spId);

    int insert(Spec record);

    int insertSelective(Spec record);

    Spec selectByPrimaryKey(Integer spId);

    int updateByPrimaryKeySelective(Spec record);

    int updateByPrimaryKeyWithBLOBs(Spec record);

    int updateByPrimaryKey(Spec record);
}
package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentCommitRecord;

import java.util.List;

public interface PaymentCommitRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentCommitRecord record);

    int insertSelective(PaymentCommitRecord record);

    List<PaymentCommitRecord> select(PaymentCommitRecord record);

    int updateByPrimaryKeySelective(PaymentCommitRecord record);

    int updateByPrimaryKey(PaymentCommitRecord record);
}
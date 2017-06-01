package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentCollectionRecord;

import java.util.List;

public interface PaymentCollectionRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentCollectionRecord record);

    void insertList(List<PaymentCollectionRecord> list);

    List<PaymentCollectionRecord> select(PaymentCollectionRecord record);

    int updateByPrimaryKeySelective(PaymentCollectionRecord record);

    int updateStatus(PaymentCollectionRecord record);
}
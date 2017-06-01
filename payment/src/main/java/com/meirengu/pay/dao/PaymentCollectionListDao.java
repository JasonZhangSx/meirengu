package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentCollectionList;

import java.util.List;

public interface PaymentCollectionListDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentCollectionList record);

    List<PaymentCollectionList> select(PaymentCollectionList record);

    PaymentCollectionList selectById(Integer id);

    int update(PaymentCollectionList record);

    int updateByPrimaryKey(PaymentCollectionList record);
}
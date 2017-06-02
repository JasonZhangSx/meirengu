package com.meirengu.pay.service;

import com.meirengu.pay.model.PaymentCollectionList;
import com.meirengu.pay.model.PaymentCollectionRecord;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/15 15:20.
 */
public interface PaymentCollectionListService {
    String insert(String content);
    String select(PaymentCollectionList paymentCollectionList);
    String selectRecord(PaymentCollectionRecord paymentCollectionRecord);
    String addRecord(String content);
}

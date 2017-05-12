package com.meirengu.pay.service;

import com.meirengu.pay.model.PaymentCommitRecord;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/11 15:44.
 */
public interface PaymentCommitRecordService {
    String insert(String content);
    String select(PaymentCommitRecord paymentCommitRecord);
}

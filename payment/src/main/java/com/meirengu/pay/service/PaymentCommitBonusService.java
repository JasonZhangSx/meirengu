package com.meirengu.pay.service;

import com.meirengu.pay.vo.PaymentCommitBonusVo;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/16 10:50.
 */
public interface PaymentCommitBonusService {
    String bonus(String content);
    String query(PaymentCommitBonusVo paymentCommitBonusVo);
    void startDistribute();
}

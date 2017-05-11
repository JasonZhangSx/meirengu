package com.meirengu.pay.service;

import com.meirengu.pay.vo.PaymentCommitListVo;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/5 12:11.
 */
public interface PaymentCommitService {
    String insert(String content);
    String update(PaymentCommitListVo paymentCommitListVo);
    String select(PaymentCommitListVo paymentCommitListVo);
    String detail(PaymentCommitListVo paymentCommitListVo);
}

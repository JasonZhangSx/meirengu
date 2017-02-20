package com.meirengu.pay.service;

import com.meirengu.pay.model.Payment;

/**
 * 支付服务
 * @author 建新
 * @create 2017-02-20 11:25
 */
public interface PaymentService {

    int insert(Payment payment);

    int update(Payment payment);
}

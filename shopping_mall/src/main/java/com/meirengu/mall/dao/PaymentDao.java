package com.meirengu.mall.dao;

import com.meirengu.mall.model.Payment;

/**
 * 支付Dao
 * @author 建新
 * @create 2017-02-20 11:29
 */
public interface PaymentDao {

    int insert(Payment payment);

    int update(Payment payment);
}

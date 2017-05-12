package com.meirengu.pay.dao;

import com.meirengu.pay.vo.PaymentCommitListVo;

import java.util.List;

public interface PaymentCommitListDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentCommitListVo record);

    List<PaymentCommitListVo> select(PaymentCommitListVo paymentCommitListVo);

    PaymentCommitListVo getById(Integer id);

    int update(PaymentCommitListVo record);
}
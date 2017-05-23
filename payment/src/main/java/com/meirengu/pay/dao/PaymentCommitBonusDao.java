package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentCommitBonus;
import com.meirengu.pay.vo.PaymentCommitBonusVo;

import java.util.List;

public interface PaymentCommitBonusDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentCommitBonus record);

    void insertList(List<PaymentCommitBonus> list);

    List<PaymentCommitBonus> select(PaymentCommitBonusVo paymentCommitBonusVo);

    int updateByPrimaryKeySelective(PaymentCommitBonus record);

    int updateStatus(PaymentCommitBonus record);
}
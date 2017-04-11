package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentInvitationBonus;

import java.math.BigDecimal;

public interface PaymentInvitationBonusDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentInvitationBonus record);

    int insertSelective(PaymentInvitationBonus record);

    PaymentInvitationBonus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PaymentInvitationBonus record);

    int updateByPrimaryKey(PaymentInvitationBonus record);

    BigDecimal selectSumPrincipal(Integer userId);
}
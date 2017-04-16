package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentInvitationBonus;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentInvitationBonusDao {

    List<PaymentInvitationBonus> getUserBonus(Integer userId);

    int deleteByPrimaryKey(Integer id);

    int insert(PaymentInvitationBonus record);

    int insertSelective(PaymentInvitationBonus record);

    PaymentInvitationBonus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PaymentInvitationBonus record);

    int updateByPrimaryKey(PaymentInvitationBonus record);

    List<PaymentInvitationBonus>  selectSumPrincipal(String userId);
}
package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentAccount;

import java.math.BigDecimal;

public interface PaymentAccountDao {

    int insertAccount(PaymentAccount record);

    PaymentAccount selectByUserId(Integer accountId);

    int updateAccount(PaymentAccount record);

    int updateBalance(Integer accountId,BigDecimal accountBalance);
}
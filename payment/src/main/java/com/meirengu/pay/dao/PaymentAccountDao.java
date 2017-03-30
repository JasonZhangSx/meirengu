package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface PaymentAccountDao {

    int insertAccount(PaymentAccount record);

    PaymentAccount selectByUserId(Integer userId);

    int updateAccount(PaymentAccount record);

    int updateBalance(@Param("accountId")Integer accountId, @Param("accountBalance")BigDecimal accountBalance);
}
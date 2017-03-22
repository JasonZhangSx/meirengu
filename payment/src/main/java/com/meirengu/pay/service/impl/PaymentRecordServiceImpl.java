package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentRecordDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.model.PaymentRecord;
import com.meirengu.pay.service.PaymentRecordService;
import com.meirengu.pay.utils.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金流水Impl
 * Author: haoyang.Yu
 * Create Date: 2017/3/14 11:35.
 */
@Service
@Transactional(readOnly = true)
public class PaymentRecordServiceImpl implements PaymentRecordService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentRecordServiceImpl.class);
    @Autowired
    private PaymentRecordDao paymentRecordDao;
    @Autowired
    private PaymentAccountDao paymentAccountDao;
    @Override
    public String insertPaymentRecord(PaymentRecord paymentRecord) {
        logger.info("Request insertPaymentRecord parameter:{}", paymentRecord.toString());
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PaymentAccount paymentAccount = paymentAccountDao.selectByUserId(paymentRecord.getUserId());
            if (paymentAccount==null){
                throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL);
            }
            paymentRecord.setUserName(paymentAccount.getAccountName());
            paymentRecord.setUserPhone(paymentAccount.getMobile());
            paymentRecord.setAccountId(paymentAccount.getAccountId());
            //支付方式、支付渠道、支付银行代码、支付类型、订单号、第三方订单号、
            return null;
        }catch (Exception e){
            logger.error("Capture insertPaymentRecord ErrorMsg:{}",e.getMessage());
            return null;
        }
    }

}

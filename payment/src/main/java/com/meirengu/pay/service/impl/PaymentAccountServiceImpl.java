package com.meirengu.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.model.Payment;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.service.PaymentAccountService;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/13 16:57.
 */
@Service
public class PaymentAccountServiceImpl extends BaseServiceImpl implements PaymentAccountService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentAccountServiceImpl.class);
    @Autowired
    private PaymentAccountDao paymentDao;
    /**
     * 查询支付账户
     * @param content 账户信息内容
     * @return 查询结果
     */
    @Override
    public String getAccountByUserId(String content) {
        Map<String,Object> map = new HashMap<>();
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            paymentAccount = (PaymentAccount) super.execute(content,paymentAccount);
            logger.info("Request getAccountByUserId parameter:{}",paymentAccount.toString());
            paymentAccount = paymentDao.selectByUserId(paymentAccount.getUserId());
            if (paymentAccount==null){
                throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL);
            }
            map.put("account",paymentAccount);
            return ResultUtil.getResult(StatusCode.PAYMENT_ACCOUNT_SUCCESS_SELECT,map);
        } catch (Exception e) {
            logger.error("Capture getAccountByUserId ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT,null);
        }
    }

    /**
     * 创建支付账户
     * @param content 账户信息内容
     * @return 创建结果
     */
    @Override
    public String createAccount(String content) {
        PaymentAccount paymentAccount = new PaymentAccount();
        PaymentAccount pay = new PaymentAccount();
        try {
            paymentAccount = (PaymentAccount) super.execute(content,paymentAccount);
            logger.info("Request createAccount parameter:{}",paymentAccount.toString());
            logger.info("createAccount Parameter check Start========>");
            Validator.getInstance().validate(paymentAccount);
            logger.info("createAccount Parameter check End<========");
            paymentAccount.setCreateTime(new Date());
            if (paymentDao.selectByUserId(paymentAccount.getUserId())!=null){
                throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_INSERT_REPEAT);
            }
            pay.setUserId(paymentAccount.getUserId());
            pay.setMobile(paymentAccount.getMobile());
            paymentDao.insertAccount(pay);
            logger.info("createAccount prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_ACCOUNT_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.PAYMENT_ACCOUNT_SUCCESS_INSERT,null);
        } catch (Exception e) {
            logger.error("Capture createAccount ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_ACCOUNT_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_ACCOUNT_ERROR_INSERT, null);
        }
    }
    /**
     * 修改支付账户
     * @param content 账户信息内容
     * @return 修改结果
     */
    @Override
    public String updateAccount(String content) {
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            PaymentAccount pay = new PaymentAccount();
            ValidateSizeHandler va = new ValidateSizeHandler();
            paymentAccount = (PaymentAccount) super.execute(content,paymentAccount);
            logger.info("updateAccount Parameter check Start========>:{}", paymentAccount.toString());
            va.validate(paymentAccount,paymentAccount.getClass().getDeclaredField("userId"));
            pay.setUserId(paymentAccount.getUserId());
            if (paymentAccount.getMobile()!=null){
                va.validate(paymentAccount,paymentAccount.getClass().getDeclaredField("mobile"));
                pay.setMobile(paymentAccount.getMobile());
            }
            if (paymentAccount.getStatus()!=null){
                va.validate(paymentAccount,paymentAccount.getClass().getDeclaredField("status"));
                pay.setStatus(paymentAccount.getStatus());
            }
            logger.info("updateAccount Parameter check End<========");
            pay.setPassword(paymentAccount.getPassword());
            paymentDao.updateAccount(pay);
            logger.info("updateAccount prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_ACCOUNT_SUCCESS_UPDATE));
            return ResultUtil.getResult(StatusCode.PAYMENT_ACCOUNT_SUCCESS_UPDATE,null);
        } catch (Exception e) {
            logger.error("Capture createAccount ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_ACCOUNT_ERROR_UPDATE), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_ACCOUNT_ERROR_UPDATE, null);
        }
    }

}

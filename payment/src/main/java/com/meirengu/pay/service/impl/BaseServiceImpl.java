package com.meirengu.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentRecordDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.PaymentTypeUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateException;
import com.meirengu.pay.utils.check.Validator;
import com.meirengu.pay.vo.PaymentRecordVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/14 15:04.
 */
public abstract class BaseServiceImpl {
    protected static String url;
    protected static String inviteUrl;
    protected static String tradeUrl="/trade/order";
    protected  static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private final static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    @Autowired
    private PaymentAccountDao paymentAccountDao;
    @Autowired
    private PaymentRecordDao paymentRecordDao;
    public Object execute(String count, AnnotationValidable object) throws PaymentException {
        logger.info("Request execute parameter:{},{}", count,object.toString());
        Object ob = new Object();
        try {
            ob = JSONObject.parseObject(count,object.getClass());
        }catch (Exception e){
            logger.error("Capture recordUtil ErrorMsg:{}",e.getMessage());
            throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_PARAMETER);
        }
        return ob;
    }
    /**
     * 流水记录公共方法
     * @param count
     * @param paymentRecord
     * @return
     * @throws PaymentException
     */
    public PaymentRecordVo recordUtil(String count, PaymentRecordVo paymentRecord, Integer payType) throws PaymentException {
        try {
            logger.info("Request recordUtil parameter:{},{},业务操作：{}", count,paymentRecord.toString(),payType);
            paymentRecord = JSONObject.parseObject(count,paymentRecord.getClass());
            logger.info("recordUtil Parameter check Start========>");
            Validator.getInstance().validate(paymentRecord);
            //幂等校验
            if (paymentRecordDao.selectPaymentRecordCount(paymentRecord)!=null){
                throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_REPEAT);
            }
            PaymentAccount paymentAccount = paymentAccountDao.selectByUserId(paymentRecord.getUserId());
            if (paymentAccount==null){
                throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL);
            }
            if (payType== PaymentTypeUtil.PaymentType_Payment||payType== PaymentTypeUtil.PaymentType_Refund){
                checkOrder(paymentRecord);
            }
            if (payType== PaymentTypeUtil.PaymentType_Payment||payType== PaymentTypeUtil.PaymentType_Withdrawals){
                if (paymentRecord.getPaymentMethod()==PaymentTypeUtil.PaymentMethod_Balance){
                    BigDecimal paymentBackBalance = paymentAccount.getAccountBalance().subtract(paymentRecord.getPaymentAmount()).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                    if (paymentBackBalance.compareTo(new BigDecimal(0.00))<0){
                        throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_PAYMENT_BALANCE_NO_INSUFFICIENT);
                    }else{
                        paymentRecord.setPaymentFrontBalance(paymentAccount.getAccountBalance());
                        paymentRecord.setPaymentBackBalance(paymentBackBalance);
                    }
                }
            }else {
                paymentRecord.setPaymentFrontBalance(paymentAccount.getAccountBalance());
                paymentRecord.setPaymentBackBalance(paymentAccount.getAccountBalance().add(paymentRecord.getPaymentAmount()));
            }

            logger.info("recordUtil Parameter check End<========");
            paymentRecord.setAccountId(paymentAccount.getAccountId());
            paymentRecord.setUserName(paymentAccount.getAccountName());
            paymentRecord.setUserPhone(paymentAccount.getMobile());
            paymentRecord.setPaymentType(payType);
            paymentRecord.setCreateTime(new Date());
            return paymentRecord;
        }catch (Exception e){
            logger.error("Capture recordUtil ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_INSERT), e.getMessage());
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT);
        }
    }

    private static void checkOrder(PaymentRecordVo paymentRecord) throws DocumentException, PaymentException {
        if (url==null){
            projectValue();
        }
        Map map = ResultUtil.judgeStatus(HttpUtil.httpGet(url+tradeUrl+"/detailsn/"+paymentRecord.getOrderSn(),null));
        if (map==null){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_NO_EXISTENT);
        }
        if (!map.get("orderAmount").equals(paymentRecord.getOrderAmount().doubleValue())){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_ORDER_AMOUNT_NO_CONFORM);
        }
        if (!map.get("costAmount").equals(paymentRecord.getPaymentAmount().doubleValue())){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_PAYMENT_AMOUNT_NO_CONFORM);
        }
    }

    public static void projectValue() throws DocumentException {
        logger.info("projectValue Initialization Start========>");
        File f = new File(Thread.currentThread().getContextClassLoader().getResource("urlConfigure.xml").getPath());
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        Element foo;
        for (Iterator i = root.elementIterator("VALUE"); i.hasNext();) {
            foo = (Element) i.next();
            url=foo.elementText("projectUrl");
            inviteUrl=foo.elementText("inviteUrl");
        }
        logger.info("projectValue Initialization End========>");
    }

}

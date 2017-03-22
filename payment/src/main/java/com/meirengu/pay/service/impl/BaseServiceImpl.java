package com.meirengu.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentRecordDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.model.PaymentRecord;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.PaymentTypeUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateException;
import com.meirengu.pay.utils.check.Validator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/14 15:04.
 */
public abstract class BaseServiceImpl {
    private static String url;
    private static String tradeUrl="/trade/order";
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
    public PaymentRecord recordUtil(String count, PaymentRecord paymentRecord,Integer payType) throws PaymentException {
        logger.info("Request execute parameter:{},{},业务操作：{}", count,paymentRecord.toString(),payType);
        try {
            paymentRecord = JSONObject.parseObject(count,paymentRecord.getClass());
            logger.info("recordUtil Parameter check Start========>");
            Validator.getInstance().validate(paymentRecord);
            //幂等校验
            if (paymentRecordDao.selectPaymentRecord(paymentRecord)!=null){
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
            paymentRecord.setAccountId(paymentAccount.getAccountId());
            paymentRecord.setPaymentType(payType);
            paymentRecord.setCreateTime(new Date());
            return paymentRecord;
        }catch (Exception e){
            logger.error("Capture recordUtil ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_INSERT), e.getMessage());
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT);
        }
    }
    private static void checkOrder(PaymentRecord paymentRecord) throws DocumentException, PaymentException {
        if (url==null){
            ossValue();
        }
        Object o = ResultUtil.judgeStatus(HttpUtil.httpGet(url+tradeUrl+"?order_sn="+paymentRecord.getOrderSn(),null));
        List list = (List) o;
        if (list.size()!=1){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_NO_EXISTENT);
        }
        Map map = (Map) list.get(0);
        if (!map.get("orderAmount").equals(paymentRecord.getOrderAmount().doubleValue())){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_ORDER_AMOUNT_NO_CONFORM);
        }
        if (!map.get("costAmount").equals(paymentRecord.getPaymentAmount().doubleValue())){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_INSERT_PAYMENT_AMOUNT_NO_CONFORM);
        }
    }
    public static void ossValue() throws DocumentException {
        File f = new File(Thread.currentThread().getContextClassLoader().getResource("urlConfigure.xml").getPath());
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        Element foo;
        for (Iterator i = root.elementIterator("VALUE"); i.hasNext();) {
            foo = (Element) i.next();
            url=foo.elementText("projectUrl");
        }
    }
    public static void main(String[] args) throws DocumentException {
//        BigDecimal b1 = new BigDecimal(-100.009);
//        BigDecimal b2 = new BigDecimal(-100.008);
//        System.out.println(b1.add(b2));
//        BigDecimal bigDecimal = new BigDecimal(-100.009);
//        System.out.println(b1.add(b2).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0.00)));
//        System.out.println(b1.add(b2).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP));
//        PaymentAccount paymentAccount = new PaymentAccount();
//        paymentAccount.setAccountName("张三");
//        paymentAccount.setUserId(1234567);
//        paymentAccount.setPassword("123456");
//        paymentAccount.setMobile("15011331617");
//        paymentAccount.setEmail("15011331617@163.com");
//        System.out.println(JSONObject.toJSON(paymentAccount).toString());
//        ossValue();
//        System.out.println(tradeUrl);
//        String result=HttpUtil.httpGet(url+tradeUrl+"?order_sn=8171049333763504",null);
//        System.out.println(result);
//        Object o = ResultUtil.judgeStatus(result);
//        if (o!=null){
//            List list = (List) o;
//            Map map = (Map) list.get(0);
//            if (!map.get("costAmount").equals(new BigDecimal(1998.199).doubleValue())){
//                System.out.println(true);
//            }
//        }
//        System.out.println(o.toString());
//        try {
//            recordUtil(JSONObject.toJSON(paymentAccount).toString(),paymentAccount);
//        } catch (PaymentException e) {
//            System.out.println("参数校验出错");
//        }
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setUserId(123456789);
        paymentRecord.setPaymentMethod(0);
        paymentRecord.setPaymentType(1);
        paymentRecord.setOrderSn("8171049333763504");
        paymentRecord.setOrderAmount(new BigDecimal(1998.19));
        paymentRecord.setPaymentAmount(new BigDecimal(1000));
        System.out.println(JSONObject.toJSON(paymentRecord).toString());
    }
}

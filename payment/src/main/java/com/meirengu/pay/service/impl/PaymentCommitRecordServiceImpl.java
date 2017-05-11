package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentCommitListDao;
import com.meirengu.pay.dao.PaymentCommitRecordDao;
import com.meirengu.pay.model.PaymentCommitRecord;
import com.meirengu.pay.service.PaymentCommitRecordService;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.Validator;
import com.meirengu.pay.vo.PaymentCommitListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/11 15:44.
 */
@Service
public class PaymentCommitRecordServiceImpl extends BaseServiceImpl implements PaymentCommitRecordService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentCommitRecordServiceImpl.class);
    @Autowired
    private PaymentCommitRecordDao paymentCommitRecordDao;
    @Autowired
    private PaymentCommitListDao paymentCommitListDao;
    @Override
    public String insert(String content) {
        PaymentCommitRecord paymentCommitRecord = new PaymentCommitRecord();
        try {
            paymentCommitRecord = (PaymentCommitRecord) super.execute(content,paymentCommitRecord);
            logger.info("Request insert parameter:{}",paymentCommitRecord.toString());
            logger.info("insert Parameter check Start========>");
            Validator.getInstance().validate(paymentCommitRecord);
            logger.info("insert Parameter check End<========");
            PaymentCommitListVo paymentCommitListVo = paymentCommitListDao.getById(paymentCommitRecord.getPaymentCommitId());
            paymentCommitRecord.setPartnerId(paymentCommitListVo.getPartnerId());
            paymentCommitRecord.setPartnerName(paymentCommitListVo.getPartnerName());
            paymentCommitRecord.setItemId(paymentCommitListVo.getItemId());
            paymentCommitRecord.setItemName(paymentCommitListVo.getItemName());
            paymentCommitRecord.setCommitTime(new Date());
            paymentCommitRecordDao.insertSelective(paymentCommitRecord);
            if (paymentCommitRecord.getCommitType()==2){

            }
            logger.info("insert prompt message:{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_RECORD_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_INSERT,null);
        }
    }

    @Override
    public String select(PaymentCommitRecord paymentCommitRecord) {
        Map<String,Object> map = new HashMap<>();
        try {
            logger.info("Request select parameter:{}",paymentCommitRecord.toString());
            map.put("paymentCommitRecordList",paymentCommitRecordDao.select(paymentCommitRecord));
            logger.info("select prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_RECORD_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture detail ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_SELECT,null);
        }
    }
}

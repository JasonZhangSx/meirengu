package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentCommitListDao;
import com.meirengu.pay.dao.PaymentCommitRecordDao;
import com.meirengu.pay.model.PaymentCommitRecord;
import com.meirengu.pay.service.PaymentCommitService;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.Validator;
import com.meirengu.pay.vo.PaymentCommitListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/5 12:11.
 */
@Service
public class PaymentCommitServiceImpl extends BaseServiceImpl implements PaymentCommitService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentCommitServiceImpl.class);
    @Autowired
    private PaymentCommitListDao paymentCommitListDao;
    @Autowired
    private PaymentCommitRecordDao paymentCommitRecordDao;
    @Override
    public String insert(String content) {
        PaymentCommitListVo paymentCommit = new PaymentCommitListVo();
        try {
            paymentCommit = (PaymentCommitListVo) super.execute(content,paymentCommit);
            logger.info("Request insert parameter:{}",paymentCommit.toString());
            logger.info("insert Parameter check Start========>");
            Validator.getInstance().validate(paymentCommit);
            logger.info("insert Parameter check End<========");
            paymentCommit.setCompletedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paymentCommit.getEndDate()+" 00:00:00"));
            paymentCommit.setStatus(Byte.valueOf("0"));
            paymentCommitListDao.insert(paymentCommit);
            logger.info("insert prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_ERROR_INSERT,null);
        }
    }

    @Override
    public String update(PaymentCommitListVo paymentCommitListVo) {
        return null;
    }

    @Override
    public String select(PaymentCommitListVo paymentCommitListVo) {
        Map<String,Object> map = new HashMap<>();
        List<PaymentCommitListVo> paymentCommitListVoList = new ArrayList<>();
        try {
            logger.info("Request select parameter:{}",paymentCommitListVo.toString());
            paymentCommitListVoList = paymentCommitListDao.select(paymentCommitListVo);
            map.put("paymentCommitListVoList",paymentCommitListVoList);
            logger.info("select prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture select ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_ERROR_SELECT,null);
        }
    }

    @Override
    public String detail(PaymentCommitListVo paymentCommitListVo) {
        Map<String,Object> map = new HashMap<>();
        try {
            logger.info("Request detail parameter:{}",paymentCommitListVo.toString());
            PaymentCommitListVo paymentCommitList = new PaymentCommitListVo();
            paymentCommitList = paymentCommitListDao.select(paymentCommitListVo).get(0);
            PaymentCommitRecord paymentCommitRecord = new PaymentCommitRecord();
            paymentCommitRecord.setPaymentCommitId(paymentCommitList.getId());
            paymentCommitList.setList(paymentCommitRecordDao.select(paymentCommitRecord));
            map.put("paymentCommitList",paymentCommitList);
            logger.info("detail prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture detail ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_ERROR_SELECT,null);
        }
    }
}

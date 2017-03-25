package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentRecordDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.model.PaymentRecord;
import com.meirengu.pay.service.CapitalService;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.PaymentTypeUtil;
import com.meirengu.pay.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资金处理ServiceImpl
 * Author: haoyang.Yu
 * Create Date: 2017/3/21 11:18.
 */
@Service
public class CapitalServiceImpl extends BaseServiceImpl implements CapitalService {
    private final static Logger logger = LoggerFactory.getLogger(CapitalServiceImpl.class);
    @Autowired
    private PaymentRecordDao paymentRecordDao;
    @Autowired
    private PaymentAccountDao paymentAccountDao;
    /**
     * 退款确认
     * @param content 内容
     * @return
     */
    @Override
    public String confirmRefund(String content) {
        PaymentRecord paymentRecord = new PaymentRecord();
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            paymentRecord = (PaymentRecord) super.execute(content,paymentRecord);
            logger.info("Request confirmRefund parameter:{}",paymentRecord.toString());
            PaymentRecord record = new PaymentRecord();
            record.setOrderSn(paymentRecord.getOrderSn());
            record = paymentRecordDao.selectPaymentRecord(record);
            Integer status = checkStatus(paymentRecord.getStatus());
            if (record==null){
                throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM_ISNULL);
            }
            paymentAccount = paymentAccountDao.selectByUserId(record.getUserId());
            if (paymentAccount == null) {
                throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL);
            }
            if (record.getPaymentMethod()== PaymentTypeUtil.PaymentMethod_Balance) {
                paymentAccountDao.updateBalance(record.getAccountId(),paymentAccount.getAccountBalance().add(record.getPaymentAmount()));
            }
            paymentRecord = new PaymentRecord();
            paymentRecord.setPaymentId(record.getPaymentId());
            paymentRecord.setStatus(status);
            paymentRecordDao.updatePaymentRecord(paymentRecord);
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_SUCCESS_REFUND_CONFIRM,null);
        } catch (Exception e) {
            logger.error("Capture confirmRefund ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM,null);
        }
    }

    /**
     * 提现确认
     * @param content 内容
     * @return
     */
    @Override
    public String confirmWithdrawals(String content) {
        PaymentRecord paymentRecord = new PaymentRecord();
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            paymentRecord = (PaymentRecord) super.execute(content,paymentRecord);
            logger.info("Request confirmWithdrawals parameter:{}",paymentRecord.toString());
            PaymentRecord record = new PaymentRecord();
            record.setOrderSn(paymentRecord.getOrderSn());
            record = paymentRecordDao.selectPaymentRecord(record);
            Integer status = checkStatus(paymentRecord.getStatus());
            if (record==null){
                throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_CONFIRM_ISNULL);
            }
            if (status==PaymentTypeUtil.PaymentStatus_Fail){
                paymentAccount = paymentAccountDao.selectByUserId(record.getUserId());
                if (paymentAccount == null) {
                    throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL);
                }
            }
            paymentRecord = new PaymentRecord();
            paymentRecord.setPaymentId(record.getPaymentId());
            paymentRecord.setStatus(status);
            paymentRecordDao.updatePaymentRecord(paymentRecord);
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_SUCCESS_REFUND_CONFIRM,null);
        } catch (Exception e) {
            logger.error("Capture confirmRefund ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM,null);
        }
    }

    private static Integer checkStatus(Integer status) throws PaymentException {
        if (status!=PaymentTypeUtil.PaymentStatus_Success||status!=PaymentTypeUtil.PaymentStatus_Fail){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_STATUS);
        }else {
            return status;
        }
    }
}

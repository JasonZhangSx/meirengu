package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentRecordDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.service.CapitalService;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.PaymentTypeUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.vo.PaymentRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            paymentRecord = (PaymentRecordVo) super.execute(content,paymentRecord);
            logger.info("Request confirmRefund parameter:{}",paymentRecord.toString());
            PaymentRecordVo record = new PaymentRecordVo();
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
            paymentRecord = new PaymentRecordVo();
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
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            paymentRecord = (PaymentRecordVo) super.execute(content,paymentRecord);
            logger.info("Request confirmWithdrawals parameter:{}",paymentRecord.toString());
            PaymentRecordVo record = new PaymentRecordVo();
            record.setOrderSn(paymentRecord.getOrderSn());
            record = paymentRecordDao.selectPaymentRecord(record);
            Integer status = checkStatus(paymentRecord.getStatus());
            if (record==null){
                throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_CONFIRM_ISNULL);
            }
            String msg = paymentRecord.getReturnMsg();
            paymentAccount = paymentAccountDao.selectByUserId(record.getUserId());
            if (paymentAccount == null) {
                throw new PaymentException(StatusCode.PAYMENT_ACCOUNT_ERROR_SELECT_ISNULL);
            }
            paymentRecord = new PaymentRecordVo();
            paymentRecord.setPaymentId(record.getPaymentId());
            paymentRecord.setStatus(status);
            paymentRecord.setReturnMsg(msg);
            paymentRecordDao.updatePaymentRecord(paymentRecord);
            sendMsg(record);
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_SUCCESS_REFUND_CONFIRM,null);
        } catch (Exception e) {
            logger.error("Capture confirmRefund ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_REFUND_CONFIRM,null);
        }
    }

    private static Integer checkStatus(Integer status) throws PaymentException {
        if (status!=PaymentTypeUtil.PaymentStatus_Success&&status!=PaymentTypeUtil.PaymentStatus_Fail){
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_STATUS);
        }else {
            return status;
        }
    }
    private void sendMsg(PaymentRecordVo paymentRecordVo){
        Map<String,String> postParameter = new HashMap<>();
        if(paymentRecordVo.getStatus()==PaymentTypeUtil.PaymentStatus_Success){
            postParameter.put("tpl_id","1790355");
        }else if (paymentRecordVo.getStatus()==PaymentTypeUtil.PaymentStatus_Fail){
            postParameter.put("tpl_id","1790356");
            postParameter.put("msg",paymentRecordVo.getReturnMsg());
        }
        postParameter.put("mobile",paymentRecordVo.getUserPhone());
        postParameter.put("datetime",new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
        postParameter.put("money",String.valueOf(paymentRecordVo.getPaymentAmount()));
        logger.info("confirmWithdrawals message : Send message Start:{}",postParameter.toString());
        HttpUtil.httpPostForm(super.url+"/sms/single_send_tpl",postParameter,"UTF-8");
        // 发送消息提醒用户
        postParameter.put("sender", Integer.toString(0));// 0默认为系统发送
        if(paymentRecordVo.getStatus()==PaymentTypeUtil.PaymentStatus_Success){
            postParameter.put("tpl_id","14986216");
        }else if (paymentRecordVo.getStatus()==PaymentTypeUtil.PaymentStatus_Fail){
            postParameter.put("tpl_id","14986217");
        }
        postParameter.put("type", Integer.toString(2));// 消息类型：1公告Announce；2提醒Remind；3信息、私信Message
        postParameter.put("receiver", String.valueOf(paymentRecordVo.getUserId()));
        com.meirengu.pay.utils.HttpUtil.httpPostForm(super.url+"/uc/notify/tpl_send",postParameter,"UTF-8");
        logger.info("confirmWithdrawals message : Send message End");
    }
}

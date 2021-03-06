package com.meirengu.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.pay.common.SmsTemplate;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentDao;
import com.meirengu.pay.dao.PaymentInvitationBonusDao;
import com.meirengu.pay.dao.PaymentRecordDao;
import com.meirengu.pay.model.Payment;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.model.PaymentInvitationBonus;
import com.meirengu.pay.service.PaymentService;
import com.meirengu.pay.utils.*;
import com.meirengu.pay.vo.PaymentRecordVo;
import com.meirengu.pay.vo.WxNotifyData;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.OrderSNUtils;
import com.meirengu.utils.RequestUtil;
import com.meirengu.utils.StringUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付相关业务
 * @author 建新
 * @create 2017-02-23 14:53
 */
@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl extends BaseServiceImpl  implements PaymentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private PaymentAccountDao paymentAccountDao;
    @Autowired
    private PaymentRecordDao paymentRecordDao;
    @Autowired
    private PaymentInvitationBonusDao paymentInvitationBonusDao;
    @Override
    @Transactional(readOnly = false)
    public int insert(Payment payment) {
        try {
            int result = paymentDao.insert(payment);
            LOGGER.info(">> insert payment return {}", result);
            if(result > 0){
                return 1;
            }else {
                return 0;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> insert payment throw error. it's {}", e);
            return 2;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Payment payment) {
        try {
            int result = paymentDao.update(payment);
            LOGGER.info(">> update payment return {}", result);
            if(result > 0){
                return 1;
            }else {
                return 0;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> update payment throw error. it's {}", e);
            return 2;
        }
    }

    /**
     * 支付成功需要完成四步
     * 1.订单状态改变(先查询)
     * 2.修改payment的流水状态(先查询)
     * 3.生成服务码
     * 4.发送服务码短信
     */
    @Override
    @Transactional(readOnly = false)
    public synchronized boolean paySuccess(WxNotifyData notifyData, HttpServletRequest request, String returnMsg) {

        LOGGER.info(">> paySuccess is starting......");
        try {
            String attach = notifyData.getAttach();
            String hospitalId = null;
            String userPhone = null;
            String itemName = null;
            String itemId = null;
            String userId = null;
            String hospitalName = null;
            String doctorId = null;
            String doctorName;
            if(!StringUtil.isEmpty(attach)){
                JSONObject attachJson = JSONObject.parseObject(attach);
                hospitalId = attachJson.get("hospital_id") == null? "" : attachJson.get("hospital_id").toString();
                userPhone = attachJson.get("user_phone") == null? "" : attachJson.get("user_phone").toString();
                itemId = attachJson.get("item_id") == null? "" : attachJson.get("item_id").toString();
                userId = attachJson.get("user_id") == null? "" : attachJson.get("user_id").toString();
                doctorId = attachJson.get("doctor_id") == null? "" : attachJson.get("doctor_id").toString();
            }

            Map<String, String> orderParams = new HashMap<>();
            orderParams.put("union_sn", notifyData.getOut_trade_no());
            HttpUtil.HttpResult orderDetail  = HttpUtil.doPostForm(ConfigUtil.getConfig("mall.order.detail.url"), orderParams);
            int orderStatusCode = orderDetail.getStatusCode();
            //http请求状态码判断
            if(orderStatusCode == 200){
                String content = orderDetail.getContent();
                JSONObject resultJson = JSON.parseObject(content);
                int code = (int) resultJson.get("code");
                //业务状态码判断
                if(code != 200){
                    LOGGER.info(">> get order status is {}", code);
                    return false;
                }
                String data = resultJson.get("data") == null ? "" : resultJson.get("data").toString();
                JSONArray dataArray = JSONArray.parseArray(data);
                if(dataArray.size() <= 0){
                    LOGGER.info(">> get order fail......");
                    return false;
                }
                JSONObject dataJson = (JSONObject) dataArray.get(0);
                if(dataJson == null){
                    LOGGER.info(">> get data is null......");
                    return false;
                }
                itemName = dataJson.get("itemName") == null ? "" : dataJson.get("itemName").toString();
                int state = dataJson.get("orderState") == null ? 0 : Integer.parseInt(dataJson.get("orderState").toString());
                if(state == 1){
                    /** 1 订单状态改变 */
                    Map<String, String> params  = new HashMap<>();
                    params.put("union_sn", notifyData.getOut_trade_no());
                    params.put("transaction_sn", notifyData.getTransaction_id());
                    params.put("bank_type", notifyData.getBank_type());
                    params.put("device_info", notifyData.getDevice_info());
                    params.put("return_msg", returnMsg);
                    HttpUtil.HttpResult result = HttpUtil.doPostForm(ConfigUtil.getConfig("mall.pay.success.url"), params);
                    int statusCode = result.getStatusCode();
                    //http请求状态码判断
                    if(statusCode == 200){
                        String content1 = result.getContent();
                        JSONObject resultJson1 = JSON.parseObject(content1);
                        int code1 = (int) resultJson1.get("code");
                        //业务状态码判断
                        if(code1 != 200){
                            LOGGER.info(">> update order status fail......");
                            return false;
                        }
                    }else {
                        return false;
                    }
                }else if(state == 3){
                    //订单状态已改变的继续
                }else {
                    LOGGER.info(">> order state is not pay......");
                    return false;
                }
            }else {
                return false;
            }

            /** 2 payment流水状态 */
            Payment payment = new Payment();
            payment.setOrderSN(notifyData.getOut_trade_no());
            payment.setTransactionSN(notifyData.getTransaction_id());
            payment.setBankType(notifyData.getBank_type());
            payment.setReturnMsg(returnMsg);
            payment.setStatus(2);

            LOGGER.info(">> update payment params is {}", JSON.toJSON(payment));
            int updateNum = this.update(payment);
            if(updateNum != 1){
                return false;
            }

            /** 3 生成服务码 */
            Map<String, String> paramsCode = new HashMap<>();
            paramsCode.put("hospital_id", hospitalId);
            paramsCode.put("order_sn", notifyData.getOut_trade_no());
            paramsCode.put("user_phone", userPhone);
            paramsCode.put("item_id", itemId);

            HttpUtil.HttpResult hr = HttpUtil.doPostForm(ConfigUtil.getConfig("service.code.gen.url"), paramsCode);
            String serviceCode = null;
            int statusCode1 = hr.getStatusCode();
            String expireTime = null;
            String hospitalTel = null;
            //http请求状态码判断
            if(statusCode1 == 200){
                String content = hr.getContent();
                JSONObject resultJson = JSON.parseObject(content);
                int code = (int) resultJson.get("code");
                //业务状态码判断
                if(code != 200){
                    LOGGER.info(">> create service code fail......");
                    return false;
                }else {
                    JSONObject data = (JSONObject) resultJson.get("data");
                    serviceCode = data.get("serviceCode") == null ? "" : data.get("serviceCode").toString();
                    expireTime = data.get("expireTime") == null ? "" : data.get("expireTime").toString();
                    //expireTime = DateAndTime.dateFormat(expireTime, "yyyy-MM-dd HH:mm:ss");
                    hospitalTel = data.get("hospitalTel") == null ? "" : data.get("hospitalTel").toString();
                }
            }else {
                return false;
            }

            LOGGER.info(">> generate service code success......");

            /** 4 发送服务码短信 */
            Map<String, String> smsParams = new HashMap<>();
            String text = SmsTemplate.PAY_SUCCESS;
            text = text.replace("#product#", itemName).replace("#service_sn#", serviceCode+"")
                    .replace("#expire_time#", expireTime).replace("#hospital_tel#", hospitalTel);
            //拼装参数
            smsParams.put("mobile", userPhone);
            smsParams.put("text", text);
            //smsParams.put("extend", mobile);
            smsParams.put("uid", notifyData.getOut_trade_no());
            smsParams.put("ip", RequestUtil.getIpAddr(request));

            LOGGER.info("send sms params is {}", JSON.toJSON(smsParams));
            //如果没有发送成功，重新发送一次
            if(!sendPaySuccessSms(smsParams)){
                sendPaySuccessSms(smsParams);
            }

            LOGGER.info(">> send sms success......");
            return true;
        }catch (Exception e){
            LOGGER.error(">> pay success throw exception: {}", e);
            return false;
        }



    }

    @Override
    public synchronized boolean payFail(WxNotifyData notifyData, HttpServletRequest request, String returnMsg) {

        LOGGER.info(">> payFail is starting......");

        /** 1 订单状态改变 */
        Map<String, String> params = new HashMap<>();
        params.put("union_sn", notifyData.getOut_trade_no());
        params.put("transaction_sn", notifyData.getTransaction_id());
        params.put("bank_type", notifyData.getBank_type());
        params.put("device_info", notifyData.getDevice_info());
        params.put("return_msg", returnMsg);
        HttpUtil.HttpResult result = HttpUtil.doPostForm(ConfigUtil.getConfig("mall.pay.fail.url"), params);
        int statusCode = result.getStatusCode();
        //http请求状态码判断
        if(statusCode == 200){
            String content = result.getContent();
            JSONObject resultJson = JSON.parseObject(content);
            int code = (int) resultJson.get("code");
            //业务状态码判断
            if(code != 200){
                LOGGER.info(">> update order status fail......");
                return false;
            }
        }else {
            return false;
        }

        /** 2 payment流水状态 */
        Payment payment = new Payment();
        payment.setOrderSN(notifyData.getOut_trade_no());
        payment.setTransactionSN(notifyData.getTransaction_id());
        payment.setBankType(notifyData.getBank_type());
        payment.setReturnMsg(returnMsg);
        payment.setStatus(3);

        LOGGER.info(">> update payment params is {}", JSON.toJSON(payment));
        int updateNum = this.update(payment);
        if(updateNum != 1){
            return false;
        }

        return true;
    }

    @Override
    public Payment detail(Payment payment) {
        return paymentDao.detail(payment);
    }

    public boolean sendPaySuccessSms(Map<String, String> smsParams){
        HttpUtil.HttpResult smsResult = HttpUtil.doPostForm(ConfigUtil.getConfig("sms.send.url"), smsParams);
        int smsCode = smsResult.getStatusCode();
        //http请求状态码判断
        if(smsCode == 200){
            String content = smsResult.getContent();
            JSONObject resultJson = JSON.parseObject(content);
            int code = (int) resultJson.get("code");
            //业务状态码判断
            if(code != 200){
                LOGGER.info(">> send pay success sms fail......");
                return false;
            }
        }else {
            return false;
        }

        return true;
    }


    /**
     * 退款申请-yhy
     * @param content
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String refund(String content) {
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        try {
            paymentRecord = super.recordUtil(content,paymentRecord, PaymentTypeUtil.PaymentType_Refund);
            paymentRecordDao.insertPaymentRecord(paymentRecord);
            LOGGER.info("refund prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_SUCCESS_REFUND_APPLY));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            LOGGER.error("Capture refund ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_REFUND_APPLY), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_REFUND_APPLY, null);
        }
    }

    /**
     * 提现申请-yhy
     * @param content
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String withdrawals(String content) {
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        try {
            paymentRecord = super.recordUtil(content,paymentRecord, PaymentTypeUtil.PaymentType_Withdrawals);
            paymentRecord.setOrderSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_WITHDRAWALS_SN_PREFIX));
            paymentRecordDao.insertPaymentRecord(paymentRecord);
            paymentAccountDao.updateBalance(paymentRecord.getAccountId(),paymentRecord.getPaymentBackBalance());
            LOGGER.info("withdrawals prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_SUCCESS_WITHDRAWALS_APPLY));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            LOGGER.error("Capture withdrawals ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_APPLY), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_WITHDRAWALS_APPLY, null);
        }
    }

    /**
     * 充值申请-yhy
     * @param content
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String recharge(String content) {
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        Map<String,Object> map = new HashMap<>();
        try {
            paymentRecord = super.recordUtil(content,paymentRecord, PaymentTypeUtil.PaymentType_Recharge);
            paymentRecord.setChannelRequestTime(new Date());
            String transactionSn = OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_RECHARGE_SN_PREFIX);
            paymentRecord.setTransactionSn(transactionSn);
            paymentRecord.setOrderSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_RECHARGE_SN_PREFIX));
            paymentRecordDao.insertPaymentRecord(paymentRecord);
            map.put("tradeNo",BaoFuUtil.pay(paymentRecord.getPaymentBankType(),paymentRecord.getBankNo(),paymentRecord.getIdentityNumber(),paymentRecord.getRealName(),paymentRecord.getMobile(),transactionSn,
                    paymentRecord.getPaymentAmount().multiply(BigDecimal.valueOf(100)).setScale(BigDecimal.ROUND_UP).toString()));
            LOGGER.info("recharge prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_SUCCESS_RECHARGE_APPLY));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            LOGGER.error("Capture recharge ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_RECHARGE_APPLY), e.getMessage());
            map.put("ErrorMsg",e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_RECHARGE_APPLY, map);
        }
    }

    /**
     * 支付申请-yhy
     * @param content
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String payment(String content) {
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        Map<String,Object> map = new HashMap<>();
        try {
            paymentRecord = super.recordUtil(content,paymentRecord, PaymentTypeUtil.PaymentType_Payment);
            paymentRecord.setChannelRequestTime(new Date());
            String transactionSn = OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_PAYMENT_SN_PREFIX);
            paymentRecord.setTransactionSn(transactionSn);
            if (paymentRecord.getPaymentMethod()==PaymentTypeUtil.PaymentMethod_Balance){
                paymentRecord.setStatus(PaymentTypeUtil.PaymentStatus_Success);
            }
            paymentRecordDao.insertPaymentRecord(paymentRecord);
            if (paymentRecord.getPaymentMethod()==PaymentTypeUtil.PaymentMethod_Balance){
                paymentAccountDao.updateBalance(paymentRecord.getAccountId(),paymentRecord.getPaymentBackBalance());
                paySuccessNotice(paymentRecord);
                map.put("paymentMethod","0");
            }else{
                map.put("tradeNo",BaoFuUtil.pay(paymentRecord.getPaymentBankType(),paymentRecord.getBankNo(),paymentRecord.getIdentityNumber(),paymentRecord.getRealName(),paymentRecord.getMobile(),transactionSn ,
                        paymentRecord.getPaymentAmount().multiply(BigDecimal.valueOf(100)).setScale(BigDecimal.ROUND_UP).toString()));
                map.put("paymentMethod","1");
            }
            LOGGER.info("payment prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_SUCCESS_PAYMENT_APPLY));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            LOGGER.error("Capture payment ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_PAYMENT_APPLY), e.getMessage());
            map.put("ErrorMsg",e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_PAYMENT_APPLY, map);
        }
    }

    /**
     * 宝付回调-yhy
     * @param content
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String baofuCallback(String content) {
        Map<String, String> map = new HashMap<>();
        PaymentRecordVo paymentRecordVo = new PaymentRecordVo();
        PaymentAccount paymentAccount = new PaymentAccount();
        try {
            map = BaoFuUtil.baofuReturn(content);
        } catch (PaymentException e) {
            LOGGER.error("Capture baofuCallback ErrorMsg : ",e.getMessage());
            return "ERROR";
        }
        PaymentRecordVo paymentRecord = new PaymentRecordVo();
        paymentRecord.setTransactionSn(map.get("trans_id"));
        paymentRecordVo = paymentRecordDao.selectPaymentRecord(paymentRecord);
        if (paymentRecordVo==null){
            LOGGER.error("Capture baofuCallback ErrorMsg : transId Is Null :{}",paymentRecord.getTransactionSn());
            return "ERROR";
        }
        paymentAccount = paymentAccountDao.selectByUserId(paymentRecordVo.getUserId());
        if (paymentAccount == null) {
            LOGGER.error("Capture baofuCallback ErrorMsg : paymentAccount Is Null :{}",paymentRecordVo.getUserId());
            return "ERROR";
        }
        if (paymentRecordVo.getStatus()!=PaymentTypeUtil.PaymentStatus_Apply){
            return "OK";
        }
        paymentRecord.setPaymentId(paymentRecordVo.getPaymentId());
        paymentRecord.setStatus(PaymentTypeUtil.PaymentStatus_Success);
        paymentRecord.setPaymentFrontBalance(paymentAccount.getAccountBalance());
        BigDecimal paymentBackBalance = paymentAccount.getAccountBalance().add(paymentRecordVo.getPaymentAmount()).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
        paymentRecord.setPaymentBackBalance(paymentBackBalance);
        paymentRecord.setChannelResponseTime(new Date());
        paymentRecord.setReturnMsg(map.get("returnContent"));
        paymentRecordDao.updatePaymentRecord(paymentRecord);
        if (super.url==null){
            try {
                projectValue();
            } catch (DocumentException e) {
                LOGGER.error("Send message Error:{}",e.getMessage());
            }
        }
        if (paymentRecordVo.getPaymentType()==PaymentTypeUtil.PaymentType_Recharge){
            LOGGER.info("payment baofuCallback message : Update Account Balance Start,FrontBalance:{}",paymentRecord.getPaymentFrontBalance());
            paymentAccountDao.updateBalance(paymentRecordVo.getAccountId(),paymentRecord.getPaymentBackBalance());
            LOGGER.info("payment baofuCallback message : Update Account Balance End,BackBalance:{}",paymentRecord.getPaymentBackBalance());
            rechargeSuccessNotice(paymentRecordVo);
        }else if (paymentRecordVo.getPaymentType()==PaymentTypeUtil.PaymentType_Payment){
            paySuccessNotice(paymentRecordVo);
        }
        return "OK";
    }

    private void paySuccessNotice(PaymentRecordVo paymentRecordVo){
        Map<String,String> postParameter = new HashMap<>();
        postParameter.put("order_sn",paymentRecordVo.getOrderSn());
        postParameter.put("payment_method",paymentRecordVo.getPaymentMethod().toString());
        postParameter.put("out_sn",paymentRecordVo.getTransactionSn());
        com.meirengu.pay.utils.HttpUtil.httpPostForm(super.url+super.tradeUrl+"/payment",postParameter,"UTF-8");
    }
    private void rechargeSuccessNotice(PaymentRecordVo paymentRecordVo)  {
        Map<String,String> postParameter = new HashMap<>();
        postParameter.put("tpl_id","1790349");
        postParameter.put("mobile",paymentRecordVo.getUserPhone());
        postParameter.put("datetime",new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format(new Date()));
        postParameter.put("money",String.valueOf(paymentRecordVo.getPaymentAmount()));
        LOGGER.info("payment baofuCallback rechargeSuccessNotice message : Send message Start:{}",postParameter.toString( ));
        com.meirengu.pay.utils.HttpUtil.httpPostForm(super.url+"/sms/single_send_tpl",postParameter,"UTF-8");
        // 发送消息提醒用户
        postParameter.put("sender", Integer.toString(0));// 0默认为系统发送
        postParameter.put("tpl_id", Integer.toString(14986211));
        postParameter.put("type", Integer.toString(2));// 消息类型：1公告Announce；2提醒Remind；3信息、私信Message
        postParameter.put("receiver", String.valueOf(paymentRecordVo.getUserId()));
        com.meirengu.pay.utils.HttpUtil.httpPostForm(super.url+"/uc/notify/tpl_send",postParameter,"UTF-8");
        LOGGER.info("payment baofuCallback rechargeSuccessNotice message : Send message End");
    }
    /**
     * 根据用户id查询交易记录
     * @param paymentRecordVo
     * @return
     */
    @Override
    public String getPaymentRecord(PaymentRecordVo paymentRecordVo) {
        Map<String,Object> map = new HashMap<>();
        try {
            if (paymentRecordVo.getStartDate()!=null&&!paymentRecordVo.getStartDate().isEmpty()){
                paymentRecordVo.setStartDate(paymentRecordVo.getStartDate()+" 00:00:00");
            }
            if (paymentRecordVo.getEndDate()!=null&&!paymentRecordVo.getEndDate().isEmpty()){
                paymentRecordVo.setEndDate(paymentRecordVo.getEndDate()+" 23:59:59");
            }
            LOGGER.info("Request getPaymentRecord parameter:{}",paymentRecordVo.toString());
            List<PaymentRecordVo> list = paymentRecordDao.getPaymentRecord(paymentRecordVo);
            List<PaymentInvitationBonus> paymentInvitationBonusList = paymentInvitationBonusDao.getUserBonus(paymentRecordVo.getUserId());
            for (PaymentInvitationBonus paymentInvitationBonus : paymentInvitationBonusList){
                PaymentRecordVo pay = new PaymentRecordVo();
                pay.setPaymentAmount(paymentInvitationBonus.getPrincipal());
                pay.setPaymentType(6);
                pay.setCreateTime(paymentInvitationBonus.getCreateTime());
                list.add(pay);
            }
            if (list==null){
                throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_SELECT_IS_NULL);
            }
            map.put("paymentList",mySort(list));
            LOGGER.info("getPaymentRecord prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            LOGGER.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_SELECT,null);
        }
    }

    private List<PaymentRecordVo> mySort(List<PaymentRecordVo> arr) { // 交换排序->冒泡排序
        PaymentRecordVo temp = null;
        boolean exchange = false;
        for (int i = 0; i < arr.size(); i++) {
            exchange = false;
            for (int j = arr.size() - 2; j >= i; j--) {
                if ((arr.get(j + 1)).getCreateTime().compareTo(
                        (arr.get(j)).getCreateTime()) >= 0) {
                    temp =  arr.get(j + 1);
                    arr.set(j + 1, arr.get(j));
                    arr.set(j, temp);
                    exchange = true;
                }
            }
            if (!exchange)
                break;
        }
        return arr;
    }
    /**
     * 根据用户id查询提现金额
     * @param userId
     * @return
     */
    @Override
    public String getWithdrawalsAmount(String userId) {
        Map<String,Object> map = new HashMap<>();
        PaymentRecordVo paymentRecordVo = new PaymentRecordVo();
        try {
            paymentRecordVo.setUserId(Integer.valueOf(userId));
            paymentRecordVo.setPaymentType(PaymentTypeUtil.PaymentType_Withdrawals);
            paymentRecordVo.setStatus(PaymentTypeUtil.PaymentStatus_Apply);
            LOGGER.info("Request getWithdrawalsAmount parameter:{}",paymentRecordVo.toString());
            List<PaymentRecordVo> list = paymentRecordDao.getPaymentRecord(paymentRecordVo);
            if (list==null){
                throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_SELECT_IS_NULL);
            }
            BigDecimal withdrawalsAmount = new BigDecimal(0);
            for (PaymentRecordVo paymentRecord : list){
                withdrawalsAmount = withdrawalsAmount.add(paymentRecord.getPaymentAmount());
            }
            map.put("withdrawalsAmount",withdrawalsAmount);
            LOGGER.info("getWithdrawalsAmount prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            LOGGER.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_SELECT,null);
        }
    }
    /**
     * 根据用户id查询邀请奖励金额
     * @param userId
     * @return
     */
    @Override
    public String getInvitationAmount(String userId) {
        LOGGER.info("Request getInvitationAmount parameter:{}",userId.toString());
        Map<String,Object> map = new HashMap<>();
        try {
            List<PaymentInvitationBonus> list = paymentInvitationBonusDao.selectSumPrincipal(userId);
            LOGGER.info("getWithdrawalsAmount prompt message: query success SumPrincipal {}",list.toString());
            map.put("invitationAmount",list);
            return ResultUtil.getResult(StatusCode.OK,map);
        }catch (Exception e){
            LOGGER.error("Capture getInvitationAmount ErrorMsg:{}",e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_RECORD_ERROR_SELECT,null);
        }
    }


}

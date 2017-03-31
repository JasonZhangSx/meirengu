package com.meirengu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.pay.model.Payment;
import com.meirengu.pay.service.PaymentService;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付模块相关接口
 * @author 建新
 * @create 2017-01-13 19:01
 */
@Controller
@RequestMapping("payment")
public class PaymentController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    /**
     * 新增支付信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insertPayment(@RequestParam(value = "order_sn", required = false) String orderSN,
                                @RequestParam(value = "pay_type", required = false) String payType,
                                @RequestParam(value = "total_fee", required = false) String totalFee,
                                @RequestParam(value = "payment_type", required = false) String paymentType,
                                @RequestParam(value = "refund_type", required = false) String refundType,
                                @RequestParam(value = "device_info", required = false) String deviceInfo,
                                @RequestParam(value = "item_id", required = false) String itemId,
                                @RequestParam(value = "user_id", required = false) String userId,
                                @RequestParam(value = "hospital_id", required = false) String hospitalId,
                                @RequestParam(value = "doctor_id", required = false) String doctorId,
                                @RequestParam(value = "item_name", required = false) String itemName,
                                @RequestParam(value = "user_phone", required = false) String userPhone,
                                @RequestParam(value = "hospital_name", required = false) String hospitalName,
                                @RequestParam(value = "doctor_name", required = false) String doctorName){

        if(StringUtil.isEmpty(orderSN) || StringUtil.isEmpty(payType) || StringUtil.isEmpty(totalFee) ||
                StringUtil.isEmpty(paymentType) || StringUtil.isEmpty(itemId) || StringUtil.isEmpty(hospitalId) ||
                StringUtil.isEmpty(userId) || StringUtil.isEmpty(itemName) || StringUtil.isEmpty(hospitalName) ||
                StringUtil.isEmpty(userPhone)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Payment payment = new Payment();
        payment.setOrderSN(orderSN);
        if(StringUtil.isInteger(payType)){
            payment.setPayType(Integer.parseInt(payType));
        }else {
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
        if(StringUtil.isNumeric(totalFee)){
            payment.setTotalFee(Double.parseDouble(totalFee));
        }else {
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
        if(StringUtil.isInteger(paymentType)){
            int paymentTypeTemp = Integer.parseInt(paymentType);
            payment.setPaymentType(paymentTypeTemp);
            //1 付款  2 退款 两种的初始状态不同
            if(paymentTypeTemp == 1){
                payment.setStatus(1);
            }else if(paymentTypeTemp == 2){
                payment.setStatus(4);
                //退款 退款方式为必填
                if(StringUtil.isEmpty(refundType)){
                    return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
                }else {
                    if(StringUtil.isInteger(refundType)){
                        payment.setRefundType(Integer.parseInt(refundType));
                    }else {
                        return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
                    }
                }
            }else {

            }
        }else {
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        if(!StringUtil.isEmpty(deviceInfo)){
            payment.setDeviceInfo(deviceInfo);
        }

        if(StringUtil.isInteger(itemId) && StringUtil.isInteger(userId) && StringUtil.isInteger(hospitalId)){
            payment.setItemId(Integer.parseInt(itemId));
            payment.setItemName(itemName);
            payment.setUserId(Integer.parseInt(userId));
            payment.setUserPhone(userPhone);
            payment.setHospitalId(Integer.parseInt(hospitalId));
            payment.setHospitalName(hospitalName);
        }else {
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        if(!StringUtil.isEmpty(doctorId) && !StringUtil.isEmpty(doctorName)){
            if(StringUtil.isInteger(doctorId)){
                payment.setDoctorId(Integer.parseInt(doctorId));
                payment.setDoctorName(doctorName);
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        payment.setCreateTime(new Date());

        int result = paymentService.insert(payment);
        if(result == 0){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else if(result == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else if(result == 2){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }else {
            LOGGER.info(">> insert payment service impl return result : {}", result);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改支付信息
     * @return
     */
    @SuppressWarnings("Duplicates")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updatePayment(@RequestParam(value = "payment_id", required = false) String paymentId,
                                @RequestParam(value = "order_sn", required = false) String orderSN,
                                @RequestParam(value = "transaction_sn", required = false) String transactionSN,
                                @RequestParam(value = "pay_type", required = false) String payType,
                                @RequestParam(value = "total_fee", required = false) String totalFee,
                                @RequestParam(value = "bank_type", required = false) String bankType,
                                @RequestParam(value = "payment_type", required = false) String paymentType,
                                @RequestParam(value = "status", required = false) String status,
                                @RequestParam(value = "refund_type", required = false) String refundType,
                                @RequestParam(value = "device_info", required = false) String deviceInfo,
                                @RequestParam(value = "return_msg", required = false) String returnMsg){

        if(StringUtil.isEmpty(paymentId) && (StringUtil.isEmpty(orderSN) || StringUtil.isEmpty(paymentType)) && (StringUtil.isEmpty(transactionSN) || StringUtil.isEmpty(paymentType))){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Payment payment = new Payment();
        if(!StringUtil.isEmpty(paymentId)){
            if(StringUtil.isInteger(paymentId)){
                payment.setId(Integer.parseInt(paymentId));
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        payment.setOrderSN(orderSN);
        payment.setTransactionSN(transactionSN);
        if(StringUtil.isEmpty(payType)){
            if(StringUtil.isInteger(payType)){
                payment.setPayType(Integer.parseInt(payType));
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        if(!StringUtil.isEmpty(totalFee)){
            if(StringUtil.isNumeric(totalFee)){
                payment.setTotalFee(Double.parseDouble(totalFee));
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        if(!StringUtil.isEmpty(bankType)){
            payment.setBankType(bankType);
        }
        if(!StringUtil.isEmpty(paymentType)){
            if(StringUtil.isInteger(paymentType)){
                payment.setPaymentType(Integer.parseInt(paymentType));
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        if(!StringUtil.isEmpty(status)){
            if(StringUtil.isInteger(status)){
                payment.setStatus(Integer.parseInt(status));
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        if(!StringUtil.isEmpty(refundType)){
            if(StringUtil.isInteger(refundType)){
                payment.setRefundType(Integer.parseInt(refundType));
            }else {
                return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }
        }

        if(!StringUtil.isEmpty(deviceInfo)){
            payment.setDeviceInfo(deviceInfo);
        }

        if(!StringUtil.isEmpty(returnMsg)){
            payment.setReturnMsg(returnMsg);
        }

        int result = paymentService.update(payment);
        if(result == 0){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }else if(result == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else if(result == 2){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }else {
            LOGGER.info(">> update payment service impl return result : {}", result);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result detail(@RequestParam(value = "transaction_sn", required = false) String transactionSN,
                         @RequestParam(value = "payment_id", required = false) String paymentId,
                         @RequestParam(value = "order_sn", required = false) String orderSN){

        if(StringUtil.isEmpty(transactionSN) && StringUtil.isEmpty(orderSN) && StringUtil.isEmpty(paymentId)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Payment payment = new Payment();
        payment.setTransactionSN(transactionSN);
        payment.setOrderSN(orderSN);

        if(!StringUtil.isEmpty(paymentId)){
            if(StringUtil.isInteger(paymentId)){
                payment.setId(Integer.parseInt(paymentId));
            }
        }

        Payment p = paymentService.detail(payment);
        if(StringUtil.isEmpty(p)){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }

        return super.setResult(StatusCode.OK, p, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /**
     * 退款申请
     * @param content
     * @return
     */
    @RequestMapping(value = "refund",method = RequestMethod.POST)
    @ResponseBody
    public String refund(@RequestParam(value = "content", required = true) String content){
        return paymentService.refund(content);
    }

    /**
     * 提现申请
     * @param content
     * @return
     */
    @RequestMapping(value = "withdrawals",method = RequestMethod.POST)
    @ResponseBody
    public String withdrawals(@RequestParam(value = "content", required = true) String content){
        return paymentService.withdrawals(content);
    }

    /**
     * 充值申请
     * @param content
     * @return
     */
    @RequestMapping(value = "recharge",method = RequestMethod.POST)
    @ResponseBody
    public String recharge(@RequestParam(value = "content", required = true) String content){
        return paymentService.recharge(content);
    }

    /**
     * 支付申请
     * @param content
     * @return
     */
    @RequestMapping(value = "payment",method = RequestMethod.POST)
    @ResponseBody
    public String payment(@RequestParam(value = "content", required = true) String content){
        return paymentService.payment(content);
    }

    /**
     * 宝付回调
     * @param data_content
     * @return
     */
    @RequestMapping(value = "baofuCallback",method = RequestMethod.GET)
    @ResponseBody
    public String baofuCallback(@RequestParam(value = "data_content", required = false) String data_content){
        return paymentService.baofuCallback(data_content);
    }
    /**
     * 根据用户id查询交易记录
     * @param userId
     * @return
     */
    @RequestMapping(value = "paymentRecord",method = RequestMethod.GET)
    @ResponseBody
    public String paymentRecord(@RequestParam(value = "userId", required = true) String userId){
        return paymentService.getPaymentRecord(userId);
    }
    /**
     * 根据用户id查询交易记录
     * @param userId
     * @return
     */
    @RequestMapping(value = "withdrawalsAmount",method = RequestMethod.GET)
    @ResponseBody
    public String getWithdrawalsAmount(@RequestParam(value = "userId", required = true) String userId){
        return paymentService.getWithdrawalsAmount(userId);
    }
}

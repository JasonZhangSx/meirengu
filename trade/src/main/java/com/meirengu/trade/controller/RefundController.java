package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Refund;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.service.RefundService;
import com.meirengu.utils.NumberUtil;
import com.meirengu.utils.OrderSNUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款记录控制类
 * Created by maoruxin on 2017/3/14.
 */
@RestController
@RequestMapping("/refund")
public class RefundController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RefundController.class);

    @Autowired
    private RefundService refundService;

    @Autowired
    private OrderService orderService;

    /**
     * 申请退款
     * @param orderId
     * @param refundMessage
     * @param userMessage
     * @return
     */
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public Result refundApply(@RequestParam(value = "order_id", required = false)Integer orderId,
                              @RequestParam(value = "refund_message", required = false) String refundMessage,
                              @RequestParam(value = "user_message", required = false) String userMessage){

        if (NumberUtil.isNullOrZero(orderId) || StringUtils.isEmpty(refundMessage) || StringUtils.isEmpty(userMessage)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        try {
            Result result = refundService.refundApply(orderId, refundMessage, userMessage);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        }  catch (OrderException oe){
            logger.error("throw OrderException: {}", oe);
            if (oe.getErrorCode()!= null && StatusCode.codeMsgMap.get(oe.getErrorCode()) != null) {
                return setResult(oe.getErrorCode(), null, StatusCode.codeMsgMap.get(oe.getErrorCode()));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        } catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 退款审核
     * @param refundId
     * @param orderId
     * @param refundState
     * @param adminMessage
     * @return
     */
    @RequestMapping(value = "/audit/{refund_id}", method = RequestMethod.POST)
    public Result refundAudit(@PathVariable("refund_id") Integer refundId,
                              @RequestParam(value = "order_id", required = false) Integer orderId,
                              @RequestParam(value = "refund_state", required = false) Integer refundState,
                              @RequestParam(value = "admin_message", required = false) String adminMessage) {

        if (NumberUtil.isNullOrZero(refundId) || NumberUtil.isNullOrZero(orderId) || NumberUtil.isNullOrZero(refundState) || StringUtils.isEmpty(adminMessage)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        try {
            Result result = refundService.refundAudit(refundId, orderId, refundState, adminMessage);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        } catch (OrderException oe){
            logger.error("throw OrderException: {}", oe);
            if (oe.getErrorCode()!= null && StatusCode.codeMsgMap.get(oe.getErrorCode()) != null) {
                return setResult(oe.getErrorCode(), null, StatusCode.codeMsgMap.get(oe.getErrorCode()));
            } else {
                return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        } catch (Exception e) {
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 退款列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param orderSn
     * @param userId
     * @param userPhone
     * @param refundState
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "order_sn", required = false) String orderSn,
                          @RequestParam(value = "user_id", required = false) Integer userId,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "refund_state", required = false) Integer refundState){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(orderSn)) {
            map.put("orderSn", orderSn);
        }
        if (!NumberUtil.isNullOrZero(userId)) {
            map.put("userId", userId);
        }
        if (StringUtils.isNotBlank(userPhone)) {
            map.put("userPhone", userPhone);
        }
        if (!NumberUtil.isNullOrZero(refundState)) {
            map.put("refundState", refundState);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            map.put("sortBy", "tb." + sortBy);
        }
        if (StringUtils.isNotBlank(order)) {
            map.put("order", "tb." + order);
        }
        Page<Refund> page = new Page<Refund>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = refundService.getListByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 退款回调
     * @param refundSn
     * @param thirdRefundSn
     * @return
     */
    @RequestMapping(value = "payment",  method = RequestMethod.POST)
    public Result paymentCallBack(@RequestParam(value = "refund_sn", required = false)String refundSn,
                                  @RequestParam(value = "third_refund_sn", required = false)String thirdRefundSn,
                                  @RequestParam(value = "payment_status", required = false)Integer paymentStatus) {
        logger.debug("退款成功回调退款订单号：{} 第三方支付号 {} 状态 {}", refundSn, thirdRefundSn, paymentStatus);
        if (StringUtils.isEmpty(refundSn) || StringUtils.isEmpty(thirdRefundSn)
                || !(paymentStatus == Constant.PAYMENT_SUCCESS || paymentStatus == Constant.PAYMENT_FAIL)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        try {
            Result result = refundService.paymentCallBack(refundSn, thirdRefundSn, paymentStatus);
            return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        } catch (Exception e) {
            logger.error("throw exception: {}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

}

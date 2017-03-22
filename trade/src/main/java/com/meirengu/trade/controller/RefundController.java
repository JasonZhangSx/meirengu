package com.meirengu.trade.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Refund;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.service.RefundService;
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
     * @param orderSN
     * @param thirdOrderSN
     * @param itemId
     * @param userId
     * @param userName
     * @param userPhone
     * @param orderAmount
     * @return
     */
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public Result refundApply(@RequestParam(value = "order_id", required = false)int orderId,
                              @RequestParam(value = "order_sn", required = false) String orderSN,
                              @RequestParam(value = "third_order_sn", required = false) String thirdOrderSN,
                              @RequestParam(value = "item_id", required = false) int itemId,
                              @RequestParam(value = "user_id", required = false) int userId,
                              @RequestParam(value = "user_name", required = false) String userName,
                              @RequestParam(value = "user_phone", required = false) String userPhone,
                              @RequestParam(value = "order_amount", required = false) BigDecimal orderAmount,
                              @RequestParam(value = "cost_amount", required = false) BigDecimal costAmount,
                              @RequestParam(value = "refund_message", required = false) String refundMessage,
                              @RequestParam(value = "user_message", required = false) String userMessage){

        if (orderId == 0 || StringUtils.isEmpty(orderSN) || StringUtils.isEmpty(thirdOrderSN)|| itemId == 0
                || userId == 0 || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone)
                || orderAmount == null || orderAmount.equals(BigDecimal.ZERO) || StringUtils.isEmpty(refundMessage)
                || StringUtils.isEmpty(userMessage)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        if (costAmount == null || costAmount.equals(BigDecimal.ZERO)){
            return setResult(StatusCode.REFUND_AMOUNT_IS_ZERO, null, StatusCode.codeMsgMap.get(StatusCode.REFUND_AMOUNT_IS_ZERO));
        }

        //退款记录表
        Refund refund = new Refund();
        refund.setRefundSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_REFUND_SN_PREFIX));
        refund.setOrderId(orderId);
        refund.setOrderSn(orderSN);
        refund.setThirdOrderSn(thirdOrderSN);
        refund.setItemId(itemId);
        refund.setPartnerId(0);//目前退款只从平台退，不涉及合作方
        refund.setUserId(userId);
        refund.setUserName(userName);
        refund.setUserPhone(userPhone);
        refund.setAddTime(new Date());
        refund.setOrderAmount(orderAmount);
        refund.setOrderRefund(costAmount);
        refund.setRefundPaymentcode("");//支付方式名称申请时为空
        refund.setRefundPaymentname("");//支付方式代码申请时为空
        refund.setRefundMessage(refundMessage);
        refund.setUserMessage(userMessage);
        refund.setRefundType(2);//类型:1为买家,2为卖家
        refund.setRefundState(1);//状态:1为待处理,2为同意,3为拒绝

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderState(OrderStateEnum.REFUND_APPLY.getValue());

        try {
            Result result = refundService.refundApply(refund, order);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        } catch (Exception e){
            logger.error("throw exception:", e);
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
    public Result refundAudit(@PathVariable("refund_id") int refundId,
                              @RequestParam(value = "order_id", required = false) int orderId,
                              @RequestParam(value = "refund_state", required = false) int refundState,
                              @RequestParam(value = "admin_message", required = false) String adminMessage) {

        if (refundId == 0 || orderId == 0 || refundState == 0 || StringUtils.isEmpty(adminMessage)) {
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        //退款申请记录表修改信息
        Refund refund = new Refund();
        refund.setRefundId(refundId);
        refund.setAdminMessage(adminMessage);
        refund.setAdminTime(new Date());
        refund.setRefundState(refundState);

        //订单表修改信息
        Order order = new Order();
        order.setOrderId(orderId);
        if (refundState == 2) {
            order.setOrderState(OrderStateEnum.REFUND_CONFIRM.getValue());
        } else if (refundState == 3) {
            order.setOrderState(OrderStateEnum.REFUND_REFUSE.getValue());
        }

        try {
            Result result = refundService.refundAudit(refund, order);
            logger.info("Request getResponse: {}", JSON.toJSON(result));
            return result;
        } catch (Exception e) {
            logger.error("throw exception:", e);
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
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "sort_by", required = false) String sortBy,
                          @RequestParam(value = "order", required = false) String order,
                          @RequestParam(value = "order_sn", required = false) String orderSn,
                          @RequestParam(value = "user_id", required = false) String userId,
                          @RequestParam(value = "user_phone", required = false) String userPhone,
                          @RequestParam(value = "refund_state", required = false) String refundState){
        Map<String, Object> map = new HashMap<>();
        map.put("orderSn", orderSn);
        map.put("userId", userId);
        map.put("userPhone", userPhone);
        map.put("refundState", refundState);
        map.put("sortBy", sortBy);
        map.put("order", order);
        Page<Refund> page = new Page<Refund>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        try{
            page = refundService.getListByPage(page, map);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


}

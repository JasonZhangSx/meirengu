package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.dao.RefundDao;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Refund;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.service.RefundService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.OrderSNUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Refund服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund> implements RefundService{

    private static final Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RefundDao refundDao;

    /**
     * 退款申请
     * @param orderId
     * @param refundMessage
     * @param userMessage
     * @return
     */
    @Transactional
    public Result refundApply(int orderId, String refundMessage, String userMessage) throws Exception{
        Result result = new Result();
        //查询该订单记录
        Order order = orderService.detail(orderId);
        if (order != null && order.getOrderId() != null) {
            //判断订单是否在支付完成后72小时之内
            Date finishTime = order.getFinishedTime();
            long time = System.currentTimeMillis() - finishTime.getTime();
            if (time > 1000*60*60*72) {
                result.setCode(StatusCode.REFUND_PERIOD_EXPIRED);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REFUND_PERIOD_EXPIRED));
                return result;
            }
            //新增退款记录表
            Refund refund = new Refund();
            refund.setRefundSn(OrderSNUtils.getOrderSNByPerfix(OrderSNUtils.CROWD_FUNDING_REFUND_SN_PREFIX));
            refund.setOrderId(orderId);
            refund.setOrderSn(order.getOrderSn());
            refund.setThirdOrderSn(order.getOutSn());
            refund.setItemId(order.getItemId());
            refund.setPartnerId(0);//目前退款只从平台退，不涉及合作方
            refund.setUserId(order.getUserId());
            refund.setUserName(order.getUserName());
            refund.setUserPhone(order.getUserPhone());
            refund.setOrderAmount(order.getOrderAmount());
            refund.setOrderRefund(order.getCostAmount());
            refund.setRefundPaymentcode("");//支付方式名称申请时为空
            refund.setRefundPaymentname("");//支付方式代码申请时为空
            refund.setRefundMessage(refundMessage);
            refund.setUserMessage(userMessage);
            refund.setRefundType(Constant.REFUND_TYPE_SELLER);//类型:1为买家,2为卖家
            refund.setRefundState(Constant.REFUND_STATE_WAIT);//状态:1为待处理,2为同意,3为拒绝
            int i = insert(refund);

            //修改订单状态
            Order updateOrder = new Order();
            updateOrder.setOrderId(orderId);
            updateOrder.setOrderState(OrderStateEnum.REFUND_APPLY.getValue());
            int j = orderService.update(updateOrder);
            if (i == 1 && j == 1) {
                result.setCode(StatusCode.OK);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                logger.error("退款申请保存失败：订单ID {} ,新增退款表 {} 条，跟新订单表 {} 条", orderId, i, j);
                throw new OrderException("退款申请失败, 订单ID ", StatusCode.REFUND_APPLY_ERROR);
            }
        } else {
            logger.error("退款申请保存失败：订单ID {} ,没有该订单记录", orderId);
            throw new OrderException("退款申请保存失败：没有该订单记录, 订单ID:  " + orderId, StatusCode.ORDER_NOT_EXIST);
        }
        return result;
    }

    /**
     * 退款审核
     * @param refundId
     * @param orderId
     * @param refundState
     * @param adminMessage
     * @return
     * @throws Exception
     */
    @Transactional
    public Result refundAudit(int refundId, int orderId, int refundState, String adminMessage) throws Exception{
        Result result = new Result();
        //退款申请记录表修改信息
        Refund refund = new Refund();
        refund.setRefundId(refundId);
        refund.setAdminMessage(adminMessage);
        refund.setAdminTime(new Date());
        refund.setRefundState(refundState);
        int i = update(refund);

        //订单表修改信息
        Order updateOrder = new Order();
        updateOrder.setOrderId(orderId);
        if (refundState == Constant.REFUND_STATE_AGREE) {
            updateOrder.setOrderState(OrderStateEnum.REFUND_CONFIRM.getValue());
        } else if (refundState == Constant.REFUND_STATE_REFUSE) {
            updateOrder.setOrderState(OrderStateEnum.REFUND_REFUSE.getValue());
        }
        int j = orderService.update(updateOrder);
        if (i == 1 && j == 1 ) {
            result.setCode(StatusCode.OK);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
        } else {
            logger.error("退款审核保存失败：退款ID {} ，订单ID {} ,更新退款表 {} 条，跟新订单表 {} 条", refundId, orderId, i, j);
            throw new OrderException("退款审核保存失败 -- StatusCode: " + StatusCode.REFUND_ADUIT_ERROR, StatusCode.REFUND_ADUIT_ERROR);
        }
        //请求支付系统退款
        //请求成功执行记录修改，否则返回给erp系统错误，操作员重新操作
        Map<String, String> paramsMap = new HashMap<String, String>();
        Order order = orderService.detail(orderId);
        JSONObject content = new JSONObject();
        content.put("userId", order.getUserId());
        content.put("mobile", order.getUserPhone());
        content.put("realName", order.getUserName());
        content.put("partnerId", order.getPartnerId());
        content.put("paymentMethod", order.getPaymentMethod());
        content.put("orderSn", order.getOrderSn());
        content.put("itemId", order.getItemId());
        content.put("itemName", order.getItemName());
        content.put("itemLevelId", order.getItemLevelId());
        content.put("itemLevelName", order.getItemLevelName());
        content.put("orderAmount", order.getOrderAmount());
        content.put("paymentAmount", order.getCostAmount());
        content.put("rebateAmount", order.getRebateAmount());
        paramsMap.put("content", content.toString());
        String url = ConfigUtil.getConfig("pay.refund.url");
        HttpUtil.HttpResult applyRefundResult = HttpUtil.doPostForm(url, paramsMap);
        logger.debug("Request: {} getResponse: {}", url, applyRefundResult);
        if (applyRefundResult.getStatusCode() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(applyRefundResult.getContent());
            int code = resultJson.getIntValue("code");
            if (code != StatusCode.OK) {
                logger.error("businesscode: {}--msg: {}" , code, StatusCode.codeMsgMap.get(code));
                throw new OrderException("请求支付网关服务申请退款接口异常 -- StatusCode: " + code, code);
            }
        } else {
            logger.error("httpcode: {}--httpcontent: {}" , applyRefundResult.getStatusCode(), applyRefundResult.getContent());
            throw new OrderException("请求支付网关服务异常 -- httpCode: " + applyRefundResult.getStatusCode(), applyRefundResult.getStatusCode());
        }
        return result;
    }
    /**
     * 退款回调
     * @param refundSn
     * @param thirdRefundSn
     * @param paymentStatus
     * @return
     */
    @Transactional
    public Result paymentCallBack(String refundSn, String thirdRefundSn, int paymentStatus) {
        Result result = new Result();
        Refund refund = refundDao.getBySn(refundSn);
        Order order = new Order();
        order.setOrderSn(refund.getOrderSn());
        order.setOrderState(paymentStatus == Constant.PAYMENT_SUCCESS ? OrderStateEnum.REFUND_SUCCESS.getValue() : OrderStateEnum.PAYMENT_FAIL.getValue());
        orderService.updateBySn(order);
        if (paymentStatus == Constant.PAYMENT_SUCCESS) {
            Refund updateRefund = new Refund();
            updateRefund.setRefundId(refund.getRefundId());
            updateRefund.setThirdRefundSn(thirdRefundSn);
            updateRefund.setUserConfirm(Constant.REFUND_USER_CONFIRM);//平台已打款成功，用户已确认
            updateRefund.setConfirmTime(new Date());
            update(updateRefund);
        }
        return result;
    }
}

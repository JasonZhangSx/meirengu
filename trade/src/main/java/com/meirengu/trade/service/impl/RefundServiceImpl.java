package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
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
import com.meirengu.utils.OrderSNUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Refund服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund> implements RefundService{

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
        result.setCode(StatusCode.OK);
        //查询该订单记录
        Order order = orderService.detail(orderId);
        if (order != null && order.getOrderId() != null) {
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
            refund.setCreateTime(new Date());
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
            int j = orderService.update(order);
            if (!(i == 1 && j == 1 )) {
                throw new OrderException("退款申请失败，请重试", StatusCode.REFUND_APPLY_ERROR);
            }
        }
        return result;
    }

    /**
     * 退款审核
     * @param refund
     * @param order
     * @return
     */
    @Transactional
    public Result refundAudit(Refund refund, Order order) throws Exception{
        Result result = new Result();
        int i = update(refund);
        int j = orderService.update(order);
        if (i == 1 && j == 1 ) {
            result.setCode(StatusCode.OK);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
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
            updateRefund.setUserConfirm(Constant.REFUND_USER_WAIT);//平台已打款成功，用户待确认
            update(updateRefund);
        }
        return result;
    }
}

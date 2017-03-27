package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.dao.RefundDao;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Refund;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.service.RefundService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param refund
     * @param order
     * @return
     */
    public Result refundApply(Refund refund, Order order) throws Exception{
        Result result = new Result();
        int i = insert(refund);
        int j = orderService.update(order);
        if (i == 1 && j == 1 ) {
            result.setCode(StatusCode.OK);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            return result;
        } else {
            throw new Exception();
        }
    }

    /**
     * 退款审核
     * @param refund
     * @param order
     * @return
     */
    public Result refundAudit(Refund refund, Order order) throws Exception{
        Result result = new Result();
        int i = update(refund);
        int j = orderService.update(order);
        if (i == 1 && j == 1 ) {
            result.setCode(StatusCode.OK);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            return result;
        } else {
            throw new Exception();
        }
    }
    /**
     * 退款回调
     * @param refundSn
     * @param thirdRefundSn
     * @param paymentStatus
     * @return
     */
    public Result paymentCallBack(String refundSn, String thirdRefundSn, int paymentStatus) {
        Result result = new Result();
        Refund refund = refundDao.getBySn(refundSn);
        Order order = new Order();
        order.setOrderSn(refund.getOrderSn());
        order.setOrderState(paymentStatus == 2 ? OrderStateEnum.REFUND_SUCCESS.getValue() : OrderStateEnum.PAYMENT_FAIL.getValue());
        orderService.updateBySn(order);
        if (paymentStatus == 2) {
            Refund updateRefund = new Refund();
            updateRefund.setRefundId(refund.getRefundId());
            updateRefund.setThirdRefundSn(thirdRefundSn);
            updateRefund.setUserConfirm(1);//平台已打款成功，用户待确认
            update(updateRefund);
        }
        return result;
    }
}

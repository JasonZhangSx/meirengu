package com.meirengu.trade.service;
import com.meirengu.model.Result;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Refund;
import com.meirengu.service.BaseService;

/**
 * Refund服务接口 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface RefundService extends BaseService<Refund>{

    /**
     * 退款申请
     * @param refund
     * @param order
     * @return
     */
    Result refundApply(Refund refund, Order order)throws Exception;

    /**
     * 退款审核
     * @param refund
     * @param order
     * @return
     * @throws Exception
     */
    Result refundAudit(Refund refund, Order order)throws Exception;

    /**
     * 退款回调
     * @param refundSn
     * @param thirdRefundSn
     * @param paymentStatus
     * @return
     */
    Result paymentCallBack(String refundSn, String thirdRefundSn, int paymentStatus);
}

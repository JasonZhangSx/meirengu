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
     * @param orderId
     * @param refundMessage
     * @param userMessage
     * @param refundSponsor
     * @return
     * @throws Exception
     */
    Result refundApply(int orderId, String refundMessage, String userMessage, String refundSponsor)throws Exception;

    /**
     * 退款审核
     * @param refundId
     * @param orderId
     * @param refundState
     * @param adminMessage
     * @param operateAccount
     * @return
     * @throws Exception
     */
    Result refundAudit(int refundId, int orderId, int refundState, String adminMessage, String operateAccount)throws Exception;

    /**
     * 退款回调
     * @param refundSn
     * @param thirdRefundSn
     * @param paymentStatus
     * @return
     */
    Result paymentCallBack(String refundSn, String thirdRefundSn, int paymentStatus);
}

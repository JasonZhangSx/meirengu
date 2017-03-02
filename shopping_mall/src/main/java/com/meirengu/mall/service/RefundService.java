package com.meirengu.mall.service;

import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.Refund;

import java.util.List;
import java.util.Map;

/**
 * 退款service
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface RefundService extends PageBaseService<Order>{

    /**
     * 申请退款
     * @param orderId
     * @param userId
     * @param userMessage
     * @param refundMessage
     * @return 返回退款的金额
     */
    Map<String, Object> refundApply(int orderId, int userId, String userMessage, String refundMessage);

    boolean agreeRefund(int refundId);

    boolean refuseRefund(int refundId);

}

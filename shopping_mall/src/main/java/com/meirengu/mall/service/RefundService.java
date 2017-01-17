package com.meirengu.mall.service;

import com.meirengu.mall.model.Order;

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
    double refundApply(int orderId, int userId, String userMessage, String refundMessage);
}

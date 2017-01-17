package com.meirengu.mall.dao;

import com.meirengu.mall.model.OrderItem;
import com.meirengu.mall.model.Refund;

import java.util.Map;

/**
 * 退款dao
 * @author 建新
 * @create 2017-01-10 19:35
 */
public interface RefundDao extends PageDao<Refund>{

    /**
     * 新增退款记录
     * @param refund
     * @return
     */
    int addRefund(Refund refund);

    /**
     * 退款记录详情
     * @param refundSN
     * @return
     */
    Refund detail(String refundSN);

}

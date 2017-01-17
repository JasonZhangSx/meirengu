package com.meirengu.mall.service;

import com.meirengu.mall.model.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单service
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface OrderService extends PageBaseService<Order>{

    /**
     * 生成订单
     * @param orderList
     * @return 返回订单号
     */
    String genOrders(List<Map<String, Object>> orderList);

    /**
     * 获取订单详情列表
     * @param unionSN
     * @return
     */
    List<Map<String, Object>> orderDetail(String unionSN);

    /**
     * 删除订单id
     * @param orderId
     * @return
     */
    boolean deleteOrder(int orderId);

    /**
     * 通过订单id获取订单详情
     * @param orderId
     * @return
     */
    Map<String, Object> getDetailById(int orderId);

}

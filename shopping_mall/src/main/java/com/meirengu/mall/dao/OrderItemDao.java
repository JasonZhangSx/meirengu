package com.meirengu.mall.dao;

import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.OrderItem;

import java.util.Map;

/**
 * 订单dao
 * @author 建新
 * @create 2017-01-10 19:35
 */
public interface OrderItemDao extends PageDao<OrderItem>{

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    Map<String, Object> detail(int orderId);

    /**
     * 修改订单状态
     * @param orderId
     * @return
     */
    int modifyState(int orderId, int state);

    /**
     * 订单项目关联表
     * @param orderItem
     * @return
     */
    int addOrderItem(OrderItem orderItem);

}

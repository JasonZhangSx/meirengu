package com.meirengu.mall.service;

import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.OrderItem;

import java.util.List;

/**
 * 订单项目关联service
 *
 * @author 建新
 * @create 2017-01-12 17:07
 */
public interface OrderItemService extends PageBaseService<OrderItem>{

    /**
     * 生成订单
     * @param orderList
     * @return
     */
    int genOrdersItem(List<OrderItem> orderList);
}

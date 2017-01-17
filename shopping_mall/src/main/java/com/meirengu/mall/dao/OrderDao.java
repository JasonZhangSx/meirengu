package com.meirengu.mall.dao;

import com.meirengu.mall.model.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单dao
 * @author 建新
 * @create 2017-01-10 19:35
 */
public interface OrderDao extends PageDao<Order>{

    /**
     * 根据联合订单编号查询订单详情列表
     * @param order
     * @return
     */
    List<Map<String, Object>> detailList(Order order);

    /**
     * 修改订单状态
     * @param order
     * @return
     */
    int modifyState(Order order);

    /**
     * 新增订单
     * @param order
     * @return
     */
    int addOrder(Order order);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    int delete(int orderId);

}

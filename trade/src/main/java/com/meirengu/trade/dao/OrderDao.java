package com.meirengu.trade.dao;
import com.meirengu.dao.BaseDao;
import com.meirengu.trade.model.Order;

import java.util.List;
import java.util.Map;

/**
 * OrderDao 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface OrderDao extends BaseDao<Order>{
    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    Map<String, Object> orderDetail(int orderId);
    /**
     * 通过订单编号更新订单消息
     * @param order
     * @return
     */
    int updateBySn(Order order);

    /**
     * 通过订单编号查询订单消息
     * @return
     */
    Map<String, Object> orderDetailBySn(String orderSn);
    /**
     * 查询用户投资金额
     * @param map
     * @return
     */
    Map<String, Object> getSumAmountByUserId(Map map);
}

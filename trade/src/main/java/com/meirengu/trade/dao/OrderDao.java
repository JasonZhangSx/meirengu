package com.meirengu.trade.dao;
import com.meirengu.trade.model.Order;
import com.meirengu.dao.BaseDao;
import org.springframework.stereotype.Repository;

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
    public Map<String, Object> orderDetail(int orderId);
}

package com.meirengu.trade.service;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.service.BaseService;
import com.meirengu.trade.common.OrderRpcException;
import com.meirengu.trade.model.Order;

import java.io.IOException;
import java.util.Map;

/**
 * Order服务接口 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface OrderService extends BaseService<Order>{

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    Map<String, Object> orderDetail (int orderId) throws IOException ;
    /**
     * 获取订单详情
     * @param orderSn
     * @return
     */
    Map<String, Object> orderDetailBySn (String orderSn) throws IOException ;
    /**
     *获取后台订单列表
     * @param page
     * @param map
     * @return
     */
    Page getSystemPage(Page page, Map map)  throws IOException;

    /**
     * 预约订单审核
     * @param order
     * @return
     */
    Result appointmentAudit(Order order)  throws IOException, OrderRpcException;
    /**
     * 新增认购订单
     * @param order
     * @return
     */
    Result insertSubscriptions(Order order, int rebateReceiveId)  throws IllegalAccessException, IOException, OrderRpcException;

    /**
     * 获取客户端订单列表
     * @param page
     * @param map
     * @return
     * @throws IOException
     */
    Page getPage(Page page, Map map)  throws IOException;
    /**
     * 新增预约订单
     * @param order
     * @return
     */
    Result insertAppointment(Order order, int rebateReceiveId)  throws IOException, OrderRpcException;

    /**
     * 取消预约
     * @param order
     * @return
     */
    Result appointmentCancel(Order order);

    /**
     * 删除订单
     * @param order
     * @return
     */
    Result deleteOrder(Order order);

    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    int getCount(Map map);

    /**
     * 通过订单编号更新订单消息
     * @param order
     * @return
     */
    int updateBySn(Order order);
    /**
     * 生成3天订单txt文件
     */
    void generateOrderTxt() throws IOException ;

}

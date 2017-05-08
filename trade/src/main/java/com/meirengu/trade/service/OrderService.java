package com.meirengu.trade.service;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.service.BaseService;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.model.Order;
import com.meirengu.rocketmq.RocketmqEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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
    Map<String, Object> orderDetail (Integer orderId) throws ParseException, IOException ;
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
    Result appointmentAudit(Order order)  throws IOException, OrderException;
    /**
     * 新增认购订单
     * @param order
     * @return
     */
    Result insertSubscriptions(Order order, Integer rebateReceiveId)  throws IllegalAccessException, IOException, OrderException;

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
    Result insertAppointment(Order order, Integer rebateReceiveId)  throws IllegalAccessException, IOException, OrderException;

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
    void paymentCallBack(Order order);
    /**
     * 生成3天订单txt文件
     */
    void generateOrderTxt() throws IOException ;

    /**
     * 用户已购份数查询
     * @param param
     */
    int getHasPurchaseCount(Map<String, Object> param);

    /**
     * 订单失效
     * @return
     */
    void orderLoseEfficacy(String orderSn) throws IOException ;

    /**
     * 订单失效前提醒
     * @return
     */
    void orderRemindForPay(String orderSn) throws IOException ;

    /**
     * 根据用户id查询用户投资金额
     * @param userIds
     * @return
     */
    List<Map<String, Object>> getSumAmountByUserIds(String userIds);

}

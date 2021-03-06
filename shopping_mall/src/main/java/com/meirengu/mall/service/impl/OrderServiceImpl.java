package com.meirengu.mall.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.common.Constants;
import com.meirengu.mall.dao.CartDao;
import com.meirengu.mall.dao.OrderDao;
import com.meirengu.mall.dao.OrderItemDao;
import com.meirengu.mall.dao.RefundDao;
import com.meirengu.mall.model.*;
import com.meirengu.mall.service.OrderService;
import com.meirengu.mall.service.PageService;
import com.meirengu.mall.service.PaymentService;
import com.meirengu.mall.utils.ConfigUtil;
import com.meirengu.mall.utils.OrderSNUtils;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2017/1/9.
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private RefundDao refundDao;

    @Autowired
    private PageService<Order> pageService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public Page<Order> getPageList(Page<Order> page, Map map) {
        return pageService.getListByPage(page, map, orderDao);
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> genOrders(List<Map<String, Object>> orderList) {
        try {
            double totalPrice = 0;
            Map<String, Object> returnMap = new HashMap<>();
            String unionSN = OrderSNUtils.getUnionSN();
            for (int i = 0; i < orderList.size(); i++) {

                String orderSN = OrderSNUtils.getOrderSN();
                Map<String, Object> orderMap = orderList.get(i);
                int doctorId = Integer.parseInt(orderMap.get("doctorId").toString());
                int cartId = Integer.parseInt(orderMap.get("cartId").toString());
                int hospitalId = Integer.parseInt(orderMap.get("hospitalId").toString());
                int userId = Integer.parseInt(orderMap.get("userId").toString());
                int itemNum = Integer.parseInt(orderMap.get("itemNum").toString());
                int itemId = Integer.parseInt(orderMap.get("itemId").toString());
                int orderFrom = Integer.parseInt(orderMap.get("orderFrom").toString());
                String itemName = orderMap.get("itemName").toString();
                double itemPrice = Double.parseDouble(orderMap.get("itemPrice").toString());
                //支付总价=项目价格*项目数量
                totalPrice = totalPrice + itemPrice*itemNum;
                String userPhone = orderMap.get("userPhone").toString();
                String itemImage = orderMap.get("itemImage").toString();
                Date createTime = new Date();

                Order order = new Order();
                order.setOrderSN(orderSN);
                order.setDoctorId(doctorId);
                order.setHospitalId(hospitalId);
                order.setUserId(userId);
                order.setUserPhone(userPhone);
                order.setAddTime(createTime);
                order.setOrderType(0);
                order.setUnionSN(unionSN);
                order.setItemAmount(itemPrice * itemNum);
                //order.setDiscount(discount);
                order.setOrderAmount(itemPrice * itemNum);
                order.setOrderState(Constants.ORDER_NO_PAY);
                order.setRefundState(Constants.REFUND_STATE_WAIT);
                order.setFlag(Constants.DEL_FLAG_FALSE);
                order.setOrderFrom(orderFrom);
                Date addTime = new Date();
                order.setAddTime(addTime);
                String needPayTimeStr = DateAndTime.dateAdd("HH", DateAndTime.convertDateToString(addTime, "yyyy-MM-dd HH:mm:ss"), Integer.parseInt(ConfigUtil.getValue("order.expire.time")));
                Date needPayTime = DateAndTime.convertStringToDate(needPayTimeStr);
                order.setNeedPayTime(needPayTime);

                int orderAddNum = orderDao.addOrder(order);
                //当订单插入成功后，再进行订单项目关联插入
                if (orderAddNum > 0) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(order.getId());
                    orderItem.setItemId(itemId);
                    orderItem.setItemName(itemName);
                    orderItem.setItemPrice(itemPrice);
                    orderItem.setItemNum(itemNum);
                    orderItem.setItemImage(itemImage);
                    orderItem.setHospitalId(hospitalId);
                    orderItem.setDoctorId(doctorId);
                    int oiAddNum = orderItemDao.addOrderItem(orderItem);
                    if (oiAddNum > 0) {
                        Map<String, Object> params = new HashMap<>();
                        //若没有购物车id，则为直接下单，不需要删除购物车数据
                        if(cartId != 0){
                            params.put("cartId", cartId);
                            params.put("state", Constants.DEL_FLAG_FALSE);
                            int delCartNum = cartDao.update(params);
                            if(delCartNum > 0){
                                //return unionSN;
                            }else {
                                //失败进行事务回滚
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return null;
                            }
                        }else{
                            //return unionSN;
                        }
                    }
                } else {
                    //失败进行事务回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return null;
                }
            }

            returnMap.put("unionSN", unionSN);
            returnMap.put("totalPrice", totalPrice);
            return returnMap;
        } catch (Exception e){
            LOGGER.error("genOrder throw exception:", e);
            //失败进行事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> orderDetail(String unionSN) {
        try {
            Order order = new Order();
            order.setUnionSN(unionSN);
            return orderDao.detailList(order);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("orderDetail throw exception:", e);
            return null;
        }

    }

    @Override
    @Transactional(readOnly = false)
    /**
     * 0 抛出异常 1 修改成功  2 修改失败（要修改的记录不存在）
     */
    public int deleteOrder(int orderId) {
        try{
            int i = orderDao.delete(orderId);
            if(i > 0){
                return 1;
            }else {
                return 2;
            }
        } catch (Exception e){
            LOGGER.error("delete order throw exception:",e);
            return 0;
        }

    }

    @Override
    public Map<String, Object> getDetailById(int orderId) {
        Order order = new Order();
        order.setId(orderId);
        try{
            List<Map<String, Object>> orderList = orderDao.detailList(order);
            if(orderList.size() == 1){
                Map<String, Object> orderMap = orderList.get(0);
                return orderMap;
            }else{
                return null;
            }
        }catch (Exception e){
            LOGGER.error("order getDetailById throw exception: ", e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int modifyStatus(Order order) {
        try{
            int i = orderDao.modifyState(order);
            if(i > 0){
                return 1;
            }else {
                return 2;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> modify order status throw exception: {}", e);
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int paySuccess(String unionSN, String transactionSN, String bankType, String deviceInfo, String returnMsg) {
        //先修改订单状态
        Order order = new Order();
        order.setUnionSN(unionSN);
        order.setOrderState(Constants.ORDER_PAID);
        order.setPaymentTime(new Date());
        int modifyNum = modifyStatus(order);
        //修改订单状态成功
        if(modifyNum > 0){
            return 1;
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return 0;
    }

    @Override
    @Transactional(readOnly = false)
    public int payFail(String unionSN, String transactionSN, String bankType, String deviceInfo, String returnMsg) {
        //先修改订单状态，然后修改支付状态
        Order order = new Order();
        order.setUnionSN(unionSN);
        order.setOrderState(Constants.ORDER_PAY_FAIL);
        order.setPaymentTime(new Date());
        int modifyNum = modifyStatus(order);
        //修改订单状态成功
        if(modifyNum > 0){
            return 1;
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return 0;
    }
}

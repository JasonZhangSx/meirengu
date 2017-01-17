package com.meirengu.mall.service.impl;

import com.meirengu.mall.common.Constants;
import com.meirengu.mall.dao.CartDao;
import com.meirengu.mall.dao.OrderDao;
import com.meirengu.mall.dao.OrderItemDao;
import com.meirengu.mall.dao.RefundDao;
import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.OrderItem;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.model.Refund;
import com.meirengu.mall.service.OrderService;
import com.meirengu.mall.service.PageService;
import com.meirengu.mall.utils.OrderSNUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Page<Order> getPageList(Page<Order> page, Map map) {
        return pageService.getListByPage(page, map, orderDao);
    }


    @Override
    @Transactional(readOnly = false)
    public String genOrders(List<Map<String, Object>> orderList) {
        try {
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
                order.setOrderFrom(orderFrom);

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
                                return null;
                            }
                        }else{
                            //return unionSN;
                        }
                    }
                } else {
                    //失败进行事务回滚
                    return null;
                }
            }

            return unionSN;
        } catch (Exception e){
            LOGGER.error("genOrder throw exception:", e);
            //失败进行事务回滚
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
    public boolean deleteOrder(int orderId) {
        try{
            int i = orderDao.delete(orderId);
            if(i > 0){
                return true;
            }else {
                return false;
            }
        } catch (Exception e){
            LOGGER.error("delete order throw exception:",e);
            return false;
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
}

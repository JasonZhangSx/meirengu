package com.meirengu.mall.service.impl;

import com.meirengu.mall.common.Constants;
import com.meirengu.mall.dao.OrderDao;
import com.meirengu.mall.dao.OrderItemDao;
import com.meirengu.mall.dao.RefundDao;
import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.model.Refund;
import com.meirengu.mall.service.PageService;
import com.meirengu.mall.service.RefundService;
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
 * 退款业务service
 *
 * @author 建新
 * @create 2017-01-16 14:45
 */
@Service
@Transactional(readOnly = true)
public class RefundServiceImpl implements RefundService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RefundServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RefundDao refundDao;

    @Autowired
    private PageService<Order> pageService;

    @Override
    public Page<Order> getPageList(Page<Order> page, Map map) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> refundApply(int orderId, int userId, String userMessage, String refundMessage) {

        Map<String, Object> resultMap = new HashMap<>();
        double price = 0;
        int state = 0; // 0 抛出异常  1 添加退款信息失败 2 订单信息不存在  3 修改订单状态失败  4 成功  5 订单状态不能退款
        //1.查出订单信息  2.添加refund数据  3.修改订单状态为退款状态
        try{
            Order order = new Order();
            order.setId(orderId);
            List<Map<String, Object>> orderDetailList = orderDao.detailList(order);
            if(orderDetailList.size() < 0 ){
                state = 2;
                resultMap.put("state", state);
                return resultMap;
            }

            Map<String, Object> orderDetail = orderDetailList.get(0);
            String orderSN = orderDetail.get("orderSN") == null ? "" : orderDetail.get("orderSN").toString();
            Integer doctorId = orderDetail.get("doctorId") == null ? null : Integer.parseInt(orderDetail.get("doctorId").toString());
            Integer hospitalId = orderDetail.get("hospitalId") == null ? null : Integer.parseInt(orderDetail.get("hospitalId").toString());
            String userPhone = orderDetail.get("userPhone") == null ? "" : orderDetail.get("userPhone").toString();
            String paymentName = orderDetail.get("paymentName") == null ? "" : orderDetail.get("paymentName").toString();
            String paymentCode = orderDetail.get("paymentCode") == null ? "" : orderDetail.get("paymentCode").toString();
            Double itemAmount = orderDetail.get("itemAmount") == null ? null : Double.parseDouble(orderDetail.get("itemAmount").toString());
            Integer orderState = orderDetail.get("orderState") == null ? null : Integer.parseInt(orderDetail.get("orderState").toString());
            if(orderState != Constants.ORDER_PAID){
                state = 5;
                resultMap.put("state", state);
                return resultMap;
            }

            Date createTime = new Date();
            String refundSN = OrderSNUtils.getRefundSN(); //退款编号
            Refund refund = new Refund();
            refund.setOrderId(orderId);
            refund.setRefundSN(refundSN);
            refund.setOrderSN(orderSN);
            refund.setDoctorId(doctorId);
            refund.setHospitalId(hospitalId);
            refund.setUserId(userId);
            refund.setUserPhone(userPhone);
            refund.setRefundPaymentcode(paymentCode);
            refund.setRefundPaymentname(paymentName);
            refund.setRefundMessage(refundMessage);
            refund.setUserMessage(userMessage);
            refund.setRefundType(Constants.REFUND_TYPE_BUYER);
            refund.setRefundState(Constants.REFUND_STATE_WAIT);
            refund.setUserConfirm(Constants.REFUND_USER_WAIT);
            refund.setAddTime(createTime);
            refund.setOrderAmount(itemAmount);
            //目前项目费用多少就退多少
            refund.setOrderRefund(itemAmount);

            int j = refundDao.addRefund(refund);
            if(j > 0){
                order.setOrderState(Constants.ORDER_REFUND_APPLY);
                order.setRefundState(Constants.REFUND_ALL);
                //修改订单状态
                int i = orderDao.modifyState(order);
                if(i > 0){
                    state = 4;
                    price = itemAmount;
                    resultMap.put("state", state);
                    resultMap.put("price", price);
                    return resultMap;
                }else{
                    //修改订单状态失败
                    state = 3;
                    resultMap.put("state", state);
                    //事务回滚
                    return resultMap;
                }
            }else{
                //添加退款信息失败
                state = 1;
                resultMap.put("state", state);
                //事务回滚
                return resultMap;
            }
        }catch (Exception e){
            LOGGER.error("apply refund throw exception: ", e);
            state = 0;
            resultMap.put("state", state);
            //事务回滚
            return resultMap;
        }
    }
}

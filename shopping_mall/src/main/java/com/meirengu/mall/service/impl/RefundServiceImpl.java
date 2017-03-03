package com.meirengu.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.common.Constants;
import com.meirengu.mall.dao.OrderDao;
import com.meirengu.mall.dao.RefundDao;
import com.meirengu.mall.model.Order;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.model.Refund;
import com.meirengu.mall.service.PageService;
import com.meirengu.mall.service.RefundService;
import com.meirengu.mall.utils.ConfigUtil;
import com.meirengu.mall.utils.OrderSNUtils;
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
        //1.查出订单信息 查询payment信息  2.添加refund数据  3.修改订单状态为退款状态
        try{
            Order order = new Order();
            order.setId(orderId);
            List<Map<String, Object>> orderDetailList = orderDao.detailList(order);
            if(orderDetailList.size() <= 0 ){
                state = 2;
                resultMap.put("state", state);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMap;
            }

            Map<String, Object> orderDetail = orderDetailList.get(0);
            String unionSN = orderDetail.get("unionSN") == null ? "" : orderDetail.get("unionSN").toString();
            Integer doctorId = orderDetail.get("doctorId") == null ? null : Integer.parseInt(orderDetail.get("doctorId").toString());
            Integer hospitalId = orderDetail.get("hospitalId") == null ? null : Integer.parseInt(orderDetail.get("hospitalId").toString());
            String userPhone = orderDetail.get("userPhone") == null ? "" : orderDetail.get("userPhone").toString();
            String paymentName = orderDetail.get("paymentName") == null ? "" : orderDetail.get("paymentName").toString();
            String paymentCode = orderDetail.get("paymentCode") == null ? "" : orderDetail.get("paymentCode").toString();
            //Double itemAmount = orderDetail.get("itemPrice") == null ? null : Double.parseDouble(orderDetail.get("itemPrice").toString());
            Integer orderState = orderDetail.get("orderState") == null ? null : Integer.parseInt(orderDetail.get("orderState").toString());
            if(orderState != Constants.ORDER_PAID){
                state = 5;
                resultMap.put("state", state);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMap;
            }

            Map<String, String> params = new HashMap<>();
            params.put("order_sn", unionSN);
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(ConfigUtil.getValue("payment.detail.url"), params);
            if(hr.getStatusCode() != 200){
                state = 5;
                resultMap.put("state", state);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMap;
            }

            String content = hr.getContent();
            JSONObject contentJson = JSONObject.parseObject(content);
            int code = contentJson.get("code") == null ? 0 : Integer.parseInt(contentJson.get("code").toString());
            if(code != 200){
                state = 5;
                resultMap.put("state", state);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMap;
            }

            JSONObject dataJson = (JSONObject) contentJson.get("data");

            Double itemAmount = dataJson.get("totalFee") == null ? null : Double.parseDouble(dataJson.get("totalFee").toString());

            Date createTime = new Date();
            String refundSN = OrderSNUtils.getRefundSN(); //退款编号
            Refund refund = new Refund();
            refund.setOrderId(orderId);
            refund.setRefundSN(refundSN);
            refund.setOrderSN(unionSN);
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
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultMap;
                }
            }else{
                //添加退款信息失败
                state = 1;
                resultMap.put("state", state);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMap;
            }
        }catch (Exception e){
            LOGGER.error("apply refund throw exception: ", e);
            state = 0;
            resultMap.put("state", state);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultMap;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public boolean agreeRefund(int refundId){
        //1 修改退款记录状态  2 调用微信退款 3 新增payment退款状态 4 修改订单为同意状态

        try {
            Refund refund = refundDao.detail(refundId);

            if(refund == null){
                LOGGER.warn(">> get refund detail by refund id: {}", refundId);
                return false;
            }

            Map<String, String> params = new HashMap<>();
            params.put("order_sn", refund.getOrderSN());
            HttpUtil.HttpResult detailResult = HttpUtil.doPostForm(ConfigUtil.getValue("payment.detail.url"), params);
            if(detailResult.getStatusCode() != 200){
                LOGGER.warn(">> get payment detail return {}", detailResult);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            String content = detailResult.getContent();
            JSONObject contentJson = JSONObject.parseObject(content);
            int code = contentJson.get("code") == null ? 0 : Integer.parseInt(contentJson.get("code").toString());
            if(code != 200){
                LOGGER.warn(">> get payment detail return {}", content);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            JSONObject dataJson = (JSONObject) contentJson.get("data");
            String transactionSN = dataJson.get("transactionSN") == null ? "" : dataJson.get("transactionSN").toString();
            Map<String, String> refundMap = new HashMap<>();

            refundMap.put("device_info", "WEB");
            refundMap.put("transaction_id", transactionSN);
            refundMap.put("out_trade_no", refund.getOrderSN());
            refundMap.put("out_refund_no", refund.getRefundSN());
            refundMap.put("total_fee", (int)(refund.getOrderAmount()*100)+"");
            refundMap.put("refund_fee", (int)(refund.getOrderRefund()*100)+"");
            //refundMap.put("refund_fee_type", CNY);

            HttpUtil.HttpResult hr = HttpUtil.doPostForm(ConfigUtil.getValue("wx.refund.url"), refundMap);
            int refundCode = hr.getStatusCode();
            if(refundCode != 200){
                LOGGER.warn(">> call refund return {}", refundCode);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            String wxRefundData = hr.getContent();
            JSONObject json = JSONObject.parseObject(wxRefundData);
            String refundSN = json.get("refundId") == null ? "" : json.get("refundId").toString();
            String returnMsg = json.get("returnMsg") == null ? "" : json.get("returnMsg").toString();

            Map<String, String> paymentMap = new HashMap<>();
            paymentMap.put("order_sn", refund.getRefundSN());
            paymentMap.put("transaction_sn", refundSN);

            paymentMap.put("pay_type", "1");
            paymentMap.put("total_fee", refund.getOrderRefund()+"");
            paymentMap.put("refund_type", "1");
            paymentMap.put("device_info", "WEB");
            paymentMap.put("payment_type", "2");
            paymentMap.put("return_msg", returnMsg);

            //新增payment退款记录
            HttpUtil.HttpResult paymentResult = HttpUtil.doPostForm(ConfigUtil.getValue("payment.insert.url"), paymentMap);
            int paymentCode = paymentResult.getStatusCode();
            if(paymentCode != 200){
                LOGGER.warn(">> insert payment return {}", paymentCode);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            //修改refund状态
            Refund refundParams = new Refund();
            refundParams.setId(refundId);
            refundParams.setRefundState(2);
            int updateNum = refundDao.updateStatus(refundParams);
            if(updateNum != 1){
                LOGGER.warn(">> update refund status return {}", updateNum);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            Order order = new Order();
            order.setId(refund.getOrderId());
            order.setOrderState(Constants.ORDER_REFUND_SUCCESS);
            order.setRefundState(Constants.REFUND_ALL);
            int orderStatus = orderDao.updateStateById(order);
            if(orderStatus != 1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            return true;
        }catch (Exception e){
            LOGGER.error(">> agreeRefund throw a exception: {}", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

    }

    @Override
    @Transactional(readOnly = false)
    public boolean refuseRefund(int refundId){
        Refund data = refundDao.detail(refundId);
        if(data == null){
            return false;
        }
        //1 修改退款记录状态 2 修改订单状态为拒绝
        Refund refund = new Refund();
        refund.setId(refundId);
        refund.setRefundState(3);
        try {
            int updateNum = refundDao.updateStatus(refund);
            if(updateNum == 1){
                Order order = new Order();
                order.setOrderState(8);
                order.setRefundState(2);
                order.setId(data.getOrderId());
                //order.setRefundAmount();
                int orderNum = orderDao.modifyState(order);
                if(orderNum == 1){
                    return true;
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }catch (Exception e){
            LOGGER.error(">> refuseRefund throw a exception: {}", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}

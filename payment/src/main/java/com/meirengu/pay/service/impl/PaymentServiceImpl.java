package com.meirengu.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.pay.dao.PaymentDao;
import com.meirengu.pay.model.Payment;
import com.meirengu.pay.service.PaymentService;
import com.meirengu.pay.utils.ConfigUtil;
import com.meirengu.pay.vo.WxNotifyData;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付相关业务
 * @author 建新
 * @create 2017-02-23 14:53
 */
@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentDao paymentDao;

    @Override
    @Transactional(readOnly = false)
    public int insert(Payment payment) {
        try {
            int result = paymentDao.insert(payment);
            LOGGER.info(">> insert payment return {}", result);
            if(result > 0){
                return 1;
            }else {
                return 0;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> insert payment throw error. it's {}", e);
            return 2;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Payment payment) {
        try {
            int result = paymentDao.update(payment);
            LOGGER.info(">> update payment return {}", result);
            if(result > 0){
                return 1;
            }else {
                return 0;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> update payment throw error. it's {}", e);
            return 2;
        }
    }

    /**
     * 支付成功需要完成四步
     * 1.订单状态改变
     * 2.修改payment的流水状态
     * 3.生成服务码
     * 4.发送服务码短信
     */
    @Override
    public boolean paySuccess(WxNotifyData notifyData) {

        LOGGER.info(">> paySuccess is starting......");
        /** 1 订单状态改变 */
        Map<String, String> params = new HashMap<>();
        params.put("union_sn", notifyData.getOut_trade_no());
        HttpUtil.HttpResult result = HttpUtil.doPostForm(ConfigUtil.getConfig("mall.pay.success.url"), params);
        int statusCode = result.getStatusCode();
        //http请求状态码判断
        if(statusCode == 200){
            String content = result.getContent();
            JSONObject resultJson = JSON.parseObject(content);
            int code = (int) resultJson.get("code");
            //业务状态码判断
            if(code != 200){
                LOGGER.info(">> update order status fail......");
                return false;
            }
        }else {
            return false;
        }

        /** 2 payment流水状态 */
        Payment payment = new Payment();
        payment.setOrderSN(notifyData.getOut_trade_no());
        payment.setTransactionSN(notifyData.getTransaction_id());
        payment.setBankType(notifyData.getBank_type());
        payment.setReturnMsg(notifyData.toString());
        payment.setStatus(2);

        LOGGER.info(">> update payment params is {}", JSON.toJSON(payment));
        int updateNum = this.update(payment);
        if(updateNum != 1){
            return false;
        }

        /** 3 生成服务码 */
        Map<String, String> paramsCode = new HashMap<>();
        /*paramsCode.put("hospital_id", hospitalId);
        paramsCode.put("order_sn", notifyData.getOut_trade_no());
        paramsCode.put("user_phone", userPhone);
        paramsCode.put("item_desc", itemName);
        paramsCode.put("original_price", originalPrice);
        paramsCode.put("order_price", orderPrice);
        paramsCode.put("rest_price", restPrice);
        paramsCode.put("item_id", itemId);*/

        HttpUtil.HttpResult hr = HttpUtil.doPostForm(ConfigUtil.getConfig("service.code.gen.url"), paramsCode);
        int serviceCode = 0;
        int statusCode1 = hr.getStatusCode();
        //http请求状态码判断
        if(statusCode1 == 200){
            String content = hr.getContent();
            JSONObject resultJson = JSON.parseObject(content);
            int code = (int) resultJson.get("code");
            //业务状态码判断
            if(code != 200){
                LOGGER.info(">> create service code fail......");
                return false;
            }else {
                JSONObject data = (JSONObject) resultJson.get("data");
                serviceCode = data.get("serviceCode") == null?0:Integer.parseInt(data.get("serviceCode").toString());
            }
        }else {
            return false;
        }

        /** 发送服务码短信 */
        Map<String, String> smsParams = new HashMap<>();
        //拼装参数
        HttpUtil.HttpResult smsResult = HttpUtil.doPostForm(ConfigUtil.getConfig("sms.template.pay.success"), smsParams);
        int smsCode = smsResult.getStatusCode();
        //http请求状态码判断
        if(smsCode == 200){
            String content = smsResult.getContent();
            JSONObject resultJson = JSON.parseObject(content);
            int code = (int) resultJson.get("code");
            //业务状态码判断
            if(code != 200){
                LOGGER.info(">> send pay success sms fail......");
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    @Override
    public boolean payFail() {
        return false;
    }
}

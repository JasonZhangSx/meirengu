package com.meirengu.pay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付相关接口调用
 * @author 建新
 * @create 2017-02-20 18:41
 */
public class PaymentUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentUtils.class);

    /**
     *
     * @param orderSN
     * @param payType
     * @param totalFee 总金额 单位为元
     * @param paymentType
     * @param refundType
     * @param deviceInfo
     * @return
     */
    public static boolean insertPayment(String orderSN, String payType, String totalFee, String paymentType, String refundType, String deviceInfo,
                                        String itemId, String itemName, String userId, String userPhone, String hospitalId, String hospitalName,
                                        String doctorId, String doctorName){

        Map<String, String> params = new HashMap<>();
        params.put("order_sn", orderSN);
        params.put("pay_type", payType);
        params.put("total_fee", totalFee);
        params.put("payment_type", paymentType);
        params.put("refund_type", refundType);
        params.put("device_info", deviceInfo);
        params.put("item_id", itemId);
        params.put("item_name", itemName);
        params.put("user_id", userId);
        params.put("user_phone", userPhone);
        params.put("hospital_id", hospitalId);
        params.put("hospital_name", hospitalName);
        params.put("doctor_id", doctorId);
        params.put("doctor_name", doctorName);
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(ConfigUtil.getConfig("mall.payment.insert.url"), params);

        LOGGER.info(">> insert payment return msg : {}", hr.toString());
        int statusCode = hr.getStatusCode();
        //http请求状态码判断
        if(statusCode == 200){
            String content = hr.getContent();
            JSONObject resultJson = (JSONObject) JSON.parseObject(content);
            int code = (int) resultJson.get("code");
            //业务状态码判断
            if(code == 200){
                LOGGER.info(">> insert payment success......");
                return true;
            }else {
                LOGGER.info(">> insert payment fail......");
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args){
        //PaymentUtils.insertPayment("23423545335353", "1", "1", "1", "", "WEB");
    }

}

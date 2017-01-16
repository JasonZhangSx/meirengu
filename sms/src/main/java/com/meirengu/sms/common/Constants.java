package com.meirengu.sms.common;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 *
 * @author Marvin
 * @create 2017-01-13 下午3:22
 */
public class Constants {

    public static final String CAPTCHA_LOGIN = "10001";
    public static final String CAPTCHA_SERVICE = "10002";

    /**
     * 短信模版
     */
    public static Map<String, String> templateMap = new HashMap<String, String>();
    static {
        templateMap.put("1690670", "【美人谷】您的动态密码为：#code#，1分钟内有效，请您尽快登录美人谷，切勿泄露或转发他人。");
        templateMap.put("1690714", "【美人谷】您购买的“#product#”支付成功！订单号#order_sn#，服务码# service_sn#。到院还需支付#money#元。联系#contact_info#。请您保存好验证码，切勿泄露或转发他人。");
        templateMap.put("1690716", "【美人谷】您购买的“#product#”于#check_time#验证成功！订单号#order_sn#，如非本人操作，请及时修改登录密码，如需帮助请联系美人谷官方客服400-886-8382。");
    }



}

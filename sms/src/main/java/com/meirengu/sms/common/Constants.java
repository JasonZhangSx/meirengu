package com.meirengu.sms.common;

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
        templateMap.put("1698394", "【美人谷】您有订单未支付，请继续支付，支付成功之后可私信医院提前7天预约，如有疑问请拨打客服电话400-866-8382");
        templateMap.put("1690714", "【美人谷】您的“#product#”服务码#service_sn#，有效期至#expire_time#，需提前7天预约。联系医院#hospital_tel#，如有疑问请拨打客服电话400-866-8382");
        templateMap.put("1690716", "【美人谷】您的“#product#”于#check_time#验证成功！如有疑问请拨打客服电话400-866-8382");
        templateMap.put("1698428", "【美人谷】您的“#product#”，订单号#order_sn#已退款，退款时间#refund_time#。如有疑问请拨打客服电话400-866-8382");
    }



}

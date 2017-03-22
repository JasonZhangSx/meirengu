package com.meirengu.pay.utils;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/18 11:51.
 */
public class PaymentTypeUtil {
    //支付
    public static final Integer PaymentType_Payment=1;
    //退款
    public static final Integer PaymentType_Refund=2;
    //充值
    public static final Integer PaymentType_Recharge=3;
    //提现
    public static final Integer PaymentType_Withdrawals=4;
    //余额支付
    public static final Integer PaymentMethod_Balance=0;
    //申请中
    public static final Integer PaymentStatus_Apply=1;
    //成功
    public static final Integer PaymentStatus_Success=2;
    //失败
    public static final Integer PaymentStatus_Fail=3;

}

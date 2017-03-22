package com.meirengu.pay.utils;

import com.meirengu.common.StatusCode;

/**
 * 支付自定义异常
 * Author: haoyang.Yu
 * Create Date: 2017/3/14 11:55.
 */
public class PaymentException extends Exception {
    public PaymentException(int code){
        super(StatusCode.codeMsgMap.get(code));
    }
}

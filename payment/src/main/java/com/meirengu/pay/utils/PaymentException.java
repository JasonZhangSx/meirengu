package com.meirengu.pay.utils;

import com.meirengu.common.StatusCode;

/**
 * 支付自定义异常
 * Author: haoyang.Yu
 * Create Date: 2017/3/14 11:55.
 */
public class PaymentException extends Exception {

    public PaymentException(Object... code){
       super(getReturnMsg(code));
    }

    private static String getReturnMsg(Object... code){
        if (StatusCode.codeMsgMap.get(code[0])==null){
            return String.valueOf(code[1]);
        }else {
            return String.valueOf(StatusCode.codeMsgMap.get(code[0]));
        }
    }

}

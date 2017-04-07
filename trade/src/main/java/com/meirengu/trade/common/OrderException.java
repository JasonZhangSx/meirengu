package com.meirengu.trade.common;

/**
 * 订单调用其他服务RPC异常，springAop默认情况下只捕获runtimeException的异常
 * Created by root on 2017/3/19.
 */
public class OrderException extends RuntimeException {

    public int errorCode;

    public OrderException(){}
    public OrderException(String msg, int errorCode){
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

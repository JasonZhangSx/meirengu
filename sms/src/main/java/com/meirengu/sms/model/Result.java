package com.meirengu.sms.model;

import com.yunpian.sdk.constants.ErrorCode;
import com.yunpian.sdk.model.BaseInfo;
import com.yunpian.sdk.model.BaseStatusInfo;
import com.yunpian.sdk.model.ErrorInfo;
import com.yunpian.sdk.model.SmsStatusInfo;

/**
 * 结果实体类
 *
 * @author Marvin
 * @create 2017-01-12 下午4:27
 */
public class Result<T> extends BaseObject {

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

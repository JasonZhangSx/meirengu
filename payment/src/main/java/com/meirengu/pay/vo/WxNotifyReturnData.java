package com.meirengu.pay.vo;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-02-15 10:40
 */
public class WxNotifyReturnData {
    /**
     * 返回状态码	return_code	是	String(16)	SUCCESS
     * SUCCESS/FAIL
     * SUCCESS表示商户接收通知成功并校验成功
     */
    private String return_code;
    /**
     * 返回信息	return_msg	否	String(128)	OK
     * 返回信息，如非空，为错误原因：
     * 签名失败
     * 参数格式校验错误
     */
    private String return_msg;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}

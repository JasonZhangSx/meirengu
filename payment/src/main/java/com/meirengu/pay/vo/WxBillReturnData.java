package com.meirengu.pay.vo;

/**
 * 微信下载账单返回数据
 * @author 建新
 * @create 2017-02-16 12:08
 */
public class WxBillReturnData {
    /**
     * 返回状态码	return_code	是	String(16)	FAIL	FAIL
     */
    private String return_code;
    /**
     * 返回信息	return_msg	否	String(128)	签名失败
     * 返回信息，如非空，为错误原因
     * 如：签名失败、参数格式错误等。
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

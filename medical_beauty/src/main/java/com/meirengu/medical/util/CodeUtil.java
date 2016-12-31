package com.meirengu.medical.util;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/31 13:24.
 */
public enum CodeUtil {

    //公用code
    CORRECT("10000","操作成功"),
    ERROR_SELECT("10001","查询失败,请重试."),
    ERROR_INSERT("10002","添加失败,请重试."),
    ERROR_UPDATE("10003","更新失败,请重试."),
    ERROR_DELETE("10004","删除失败,请重试."),
    ERROR("10005","未知错误,请联系客服.");

    private String code;
    private String msg;

    private CodeUtil(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public static String getMsg(String code) {
        for (CodeUtil c : CodeUtil.values()) {
            if (c.getCode().equals(code)) {
                return c.getMsg();
            }
        }
        return "No Fund Msg";
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

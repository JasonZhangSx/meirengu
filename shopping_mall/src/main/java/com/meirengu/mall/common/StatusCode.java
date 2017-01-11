package com.meirengu.mall.common;

/**
 * 状态码集
 * Created by 建新 on 2016/12/29.
 */
public class StatusCode {
    /** 成功 **/
    public static final String STATUS_200 = "200";
    public static final String STATUS_200_MSG = "ok";
    /** 客户端参数错误 **/
    public static final String STATUS_400 = "400";
    public static final String STATUS_400_MSG = "客户端参数错误";
    /** 服务器端错误 **/
    public static final String STATUS_500 = "500";
    public static final String STATUS_500_MSG = "服务器端错误";
    /** 获取值为空 **/
    public static final String STATUS_501 = "501";
    public static final String STATUS_501_MSG = "获取内容为空";
    /** 类型转换异常 **/
    public static final String STATUS_502 = "502";
    public static final String STATUS_502_MSG = "类型转换异常";
    /** %1$s已存在 **/
    public static final String STATUS_503 = "503";
    public static final String STATUS_503_MSG = "%1$s已存在";
    /** 运行时异常 **/
    public static final String STATUS_504 = "504";
    public static final String STATUS_504_MSG = "运行时异常！";
    /** %1$s已存在 **/
    public static final String STATUS_4210 = "4210";
    public static final String STATUS_4210_MSG = "参数错误！ %1$s为必填项！";
}

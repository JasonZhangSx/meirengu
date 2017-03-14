package com.meirengu.news.common;

/**
 * 状态码集
 *
 * @author 建新
 * @create 2017-01-10 19:35
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
    /** 参数错误！ 传入JSON格式参数错误！ **/
    public static final String STATUS_4211 = "4211";
    public static final String STATUS_4211_MSG = "参数错误！ 传入JSON格式参数错误！";
    /** 至少购买一件商品！ **/
    public static final String STATUS_4212 = "4212";
    public static final String STATUS_4212_MSG = "至少购买一件商品！";
    /** 要操作的记录不存在！ **/
    public static final String STATUS_4213 = "4213";
    public static final String STATUS_4213_MSG = "要操作的记录不存在！";
    /** 该订单状态下不能进行退款操作 **/
    public static final String STATUS_4214 = "4214";
    public static final String STATUS_4214_MSG = "该订单状态下不能进行退款操作！";
    /** 该订单状态下不能进行退款操作 **/
    public static final String STATUS_4215 = "4215";
    public static final String STATUS_4215_MSG = "%1$s和%2$s必须同时存在！";

}

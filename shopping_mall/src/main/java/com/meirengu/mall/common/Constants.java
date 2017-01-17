package com.meirengu.mall.common;

/**
 * 常量表
 *
 * @author 建新
 * @create 2017-01-10 19:35
 */
public class Constants {
    /** 当前页默认值 **/
    public static final int PAGE_NUM_DEFAULT = 1;
    /** 每页显示的条数默认值 **/
    public static final int PAGE_SIZE_DEFAULT = 10;
    /** 确认删除 **/
    public static final int DEL_FLAG_TRUE = 0;
    /** 不删除 **/
    public static final int DEL_FLAG_FALSE = 1;
    //购物车状态
    /** 购物车未生成订单状态 **/
    public static final int CART_STATE_DEFAULT = 0;
    /** 购物车生成订单状态 **/
    public static final int CART_STATE_TO_ORDER = 1;
    /** 购物车删除状态 **/
    public static final int CART_STATE_DEL = 2;
    //订单全部状态
    /** 订单未付款状态 **/
    public static final int ORDER_NO_PAY = 1;
    /** 订单已失效状态 **/
    public static final int ORDER_NO_PAY_EXPIRED = 2;
    /** 订单已付款状态（未消费） **/
    public static final int ORDER_PAID = 3;
    /** 订单已失效状态 **/
    public static final int ORDER_NO_CONSUMED_EXPIRED = 4;
    /** 订单已消费状态 **/
    public static final int ORDER_CONSUMED = 5;
    /** 订单申请退款 **/
    public static final int ORDER_REFUND_APPLY = 6;
    /** 订单确认退款 **/
    public static final int ORDER_REFUND_CONFIRM = 7;
    /** 订单拒绝退款 **/
    public static final int ORDER_REFUND_REFUSE = 8;
    /** 订单退款成功 **/
    public static final int ORDER_REFUND_SUCCESS = 9;
    /** 无退款 **/
    public static final int REFUND_NO = 0;
    /** 部分退款 **/
    public static final int REFUND_PART = 1;
    /** 全部退款 **/
    public static final int REFUND_ALL = 2;
    /** 退款状态：待处理 **/
    public static final int REFUND_STATE_WAIT = 1;
    /** 退款状态：同意 **/
    public static final int REFUND_STATE_AGREE = 2;
    /** 退款状态：拒绝 **/
    public static final int REFUND_STATE_REFUSE = 3;
    /** 退款用户状态：待确认 **/
    public static final int REFUND_USER_WAIT = 1;
    /** 退款用户状态：已确认 **/
    public static final int REFUND_USER_CONFIRM = 2;
    /** 退款类型：买家 **/
    public static final int REFUND_TYPE_BUYER = 1;
    /** 退款类型：卖家 **/
    public static final int REFUND_TYPE_SELLER = 2;

}

package com.meirengu.trade.common;

/**
 * 这里放置各种常量
 */
public class Constant {

    /** YES */
    public static final int NO = 0;
    /** NO */
    public static final int YES = 1;
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
    /** 支付结果：成功 **/
    public static final int PAYMENT_SUCCESS = 2;
    /** 支付结果：失败 **/
    public static final int PAYMENT_FAIL = 3;
    /** 订单类型：普通 **/
    public static final int ORDER_TYPE_ORDINARY = 1;
    /** 订单类型：其他 **/
    public static final int ORDER_TYPE_OTHER = 2;
    /** 删除 **/
    public static final int DELETE = 0;
    /** 未删除 **/
    public static final int NOT_DELETE = 1;
    /** 订单支付方式：余额 **/
    public static final int PAYMENT_METHOD_BALANCE = 1;
    /** 订单支付方式：第三方 **/
    public static final int PAYMENT_METHOD_THIRD = 2;
    /** 优惠券类别：1无条件使用(订单免M元),2有条件使用(满N元减M元) **/
    public static final int REBATE_TYPE_UNCONDITIONAL = 1;
    public static final int REBATE_TYPE_CONDITIONAL = 2;

    /** 优惠券使用范围：1固定项目，2某类项目，3所有项目 **/
    public static final int REBATE_USE_SPECIFIC = 1;
    public static final int REBATE_USE_CATEGORY = 2;
    public static final int REBATE_USE_ALL = 3;

    /** 优惠券过期类型 **/
    /** 按绝对时间过期 **/
    public static final int REBATE_EXPIRE_BY_ABSOLUTE_TIME = 1;
    /** 按相对时间过期 **/
    public static final int REBATE_EXPIRE_BY_RELATIVE_TIME = 2;
    /** 用户优惠券状态：1未使用，2已使用，3已失效 **/
    public static final int REBATE_RECEIVE_UNUSED = 1;
    public static final int REBATE_RECEIVE_USED = 2;
    public static final int REBATE_RECEIVE_EXPIRED = 3;
    /** 用户优惠券限领次数：1每天一次，2永久一次，3不限次数 **/
    public static final int REBATE_LIMIT_DIURNAL = 1;
    public static final int REBATE_LIMIT_SINGLE = 2;
    public static final int REBATE_LIMIT_UNLIMITED = 3;








    /** 项目档位状态 */
    /** 1:预约中； */
    public static final int LEVEL_APPOINTING = 1;
    /** 2:已预约满； */
    public static final int LEVEL_APPOINT_FULL = 2;
    /** 3候补中；*/
    public static final int LEVEL_CANDIDITING = 3;
    /** 4认购中； */
    public static final int LEVEL_CROWDING = 4;
    /** 5:已完成； */
    public static final int LEVEL_COMPLETED = 5;
    /** 项目状态 */
    /** 1:新建中； */
    public static final int ITEM_BUILDING = 1;
    /** 2:初审中； */
    public static final int ITEM_FIRST_VERIFY = 2;
    /** 3初审通过；*/
    public static final int ITEM_FIRST_VERIFY_SUCCESS = 3;
    /** 4初审未通过； */
    public static final int ITEM_FIRST_VERIFY_FAIL = 4;
    /** 5:设置合作中； */
    public static final int ITEM_OPERATION = 5;
    /** 6:复审中； */
    public static final int ITEM_REVIEW_VERIFY = 6;
    /** 7复审通过； */
    public static final int ITEM_REVIEW_VERIFY_SUCCESS = 7;
    /** 8复审未通过； */
    public static final int ITEM_REVIEW_VERIFY_FAIL = 8;
    /** 9待发布；  */
    public static final int ITEM_PUBLISH_WAIT = 9;
    /** 10:预热中；*/
    public static final int ITEM_PERHEARTING = 10;
    /** 11认筹中；*/
    public static final int ITEM_CROWDING = 11;
    /** 12已完成； */
    public static final int ITEM_COMPLETED = 12;
    /** 13已下架 */
    public static final int ITEM_OFF = 13;
    /** 众筹分类 */
    /** 1 产品众筹 */
    public static final int TYPE_PRODUCT = 1;
    /** 2 收益权众筹 */
    public static final int TYPE_PROFIT = 2;
    /** 3 股权众筹 */
    public static final int TYPE_SHARES = 3;

}

package com.meirengu.erp.common;

/**
 * 常量类
 *
 * @author Marvin
 * @create 2017-01-13 下午3:22
 */
public class Constants {
    public static final int STATUS_YES = 1;

    public static final int STATUS_NO = 0;
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
    /** 感兴趣的项目 */
    /**感兴趣 */
    public static final int BE_INTERESTED = 1;
    /**不感兴趣 */
    public static final int NOT_BE_INTERESTED = 0;
    /** 放款方式：1、一次性； */
    public static final int LOAN_MODE_ONCE = 1;
    /** 放款方式：2、两次放款 */
    public static final int LOAN_MODE_TWICE = 2;
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
    /** 操作审核记录 */
    /** 1初审 */
    public static final int RECORD_FIRST_VERIFY = 1;
    /** 2设置合作 */
    public static final int LEVEL_OPERATION = 2;
    /** 3:复审； */
    public static final int LEVEL_REVIEW_VERIFY = 3;
    /** 4发布 */
    public static final int LEVEL_PUBLISH = 4;
    /** 5下架 */
    public static final int LEVEL_OFF = 5;


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

}

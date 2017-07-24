package com.meirengu.common;

/**
 * 常量表
 * Created by 建新 on 2016/12/30.
 */
public class Constants {

    public static final int STATUS_YES = 1;

    public static final int STATUS_NO = 0;

    /** 当前页默认值 **/
    public static final int PAGE_NUM_DEFAULT = 1;
    /** 每页显示的条数默认值 **/
    public static final int PAGE_SIZE_DEFAULT = 10;
    /** 确认删除 **/
    public static final int DEL_FLAG_TRUE = 0;
    /** 不删除 **/
    public static final int DEL_FLAG_FALSE = 1;
    /** redis ip白名单前缀 缓存 */
    public static final String IP_WHITE_PREFIX = "mrg_white_ip_";
}

package com.meirengu.admin.service.impl;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/24 17:23.
 * 切换数据库
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return ((String) contextHolder.get());
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}

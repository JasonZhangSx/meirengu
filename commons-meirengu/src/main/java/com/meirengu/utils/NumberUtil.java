package com.meirengu.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * 扩展NumberUtils
 * Created by root on 2017/4/26.
 */
public class NumberUtil extends NumberUtils {

    /**
     * 判断数字是否为0
     * @param n
     * @return
     */
    public static boolean isNullOrZero(Number n) {
        if(n == null) return true;
        BigDecimal nVal;
        if (n instanceof Integer) {
            nVal = new BigDecimal(n.intValue());
        } else if (n instanceof Long) {
            nVal = new BigDecimal(n.longValue());
        } else if (n instanceof Float) {
            nVal = new BigDecimal(n.floatValue());
        } else if (n instanceof Double) {
            nVal = new BigDecimal(n.doubleValue());
        } else if (n instanceof Byte) {
            nVal = new BigDecimal(n.byteValue());
        } else if (n instanceof Short) {
            nVal = new BigDecimal(n.shortValue());
        } else if (n instanceof BigDecimal) {
            nVal = new BigDecimal(n.doubleValue());
        } else {
            return true;
        }
        if (nVal.compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        return true;
    }

}

package com.meirengu.admin.util.check;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 14:35.
 * 校验整型数据，可以指定最大值或最小值。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateInt {
    String attributeValue();

    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;

    String message() default "Value of the integer is not in expected scope.";
}

package com.meirengu.pay.utils.check;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 14:42.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidatePattern {
    String attributeValue();

    String pattern();

    String message() default "This value is not valid format.";
}
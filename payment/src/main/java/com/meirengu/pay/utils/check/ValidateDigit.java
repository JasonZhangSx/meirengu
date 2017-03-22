package com.meirengu.pay.utils.check;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 15:08.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateDigit {
    String attributeValue() default "";

    String message() default "The value should be digit only.";
}

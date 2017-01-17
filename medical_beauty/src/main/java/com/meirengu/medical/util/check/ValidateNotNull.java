package com.meirengu.medical.util.check;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 14:40.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateNotNull {
    String attributeValue();

    String message() default "This value should not null.";
}

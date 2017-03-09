package com.meirengu.admin.util.check;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 14:44.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateSize {
    String attributeValue();

    String minSize() default "";

    String maxSize() default "";

    String message() default "This value is not valid format.";
}

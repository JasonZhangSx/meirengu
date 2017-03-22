package com.meirengu.pay.utils.check;
import java.lang.reflect.Field;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 14:36.
 */
public interface Handler {

    public void validate(AnnotationValidable validatedObj, Field field) throws ValidateException;
}

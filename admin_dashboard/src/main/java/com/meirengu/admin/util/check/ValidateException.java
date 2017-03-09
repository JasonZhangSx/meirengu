package com.meirengu.admin.util.check;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/7 14:37.
 */
public class ValidateException extends Exception{

    /**
     * generatored serial ID
     */
    private static final long serialVersionUID = -5374157736257347033L;

    public ValidateException(Exception ex){
        super(ex.getMessage(), ex);
    }

    public ValidateException(String message){
        super(message);
    }

    public ValidateException(String message, Exception ex){
        super(message, ex);
    }
}
package com.meirengu.uc.utils;

import com.meirengu.common.PasswordEncryption;
import com.meirengu.uc.controller.VerifyController;
import com.meirengu.uc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/6/19.
 */
public class Common {

    private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);

    public static Boolean vertifyPassword(String password,User user){
        try {
            Boolean result = PasswordEncryption.validatePassword(password,user.getPassword());
            return  result;
        }catch (Exception e){
            logger.error("Common.validatePassword throws Exception :{}" ,e.getMessage());
            return  false;
        }
    }
}

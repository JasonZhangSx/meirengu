package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.utils.RedisUtil;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huoyan403 on 3/24/2017.
 */
@RestController
@RequestMapping("verity")
public class VerifyController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);

    @Autowired
    private VerityService verityService;
    @Autowired
    UserService userService;

    //验证实名绑定卡到哪一阶段了
    @RequestMapping(value = "test",method = {RequestMethod.POST})
    public Result update(@RequestParam(value = "user_id", required = false)Integer userId,
                         @RequestParam(value = "token", required = false)String token) {
        //判断有无token
        if(!StringUtil.isEmpty(token)){
            //判断token是否有效
                RedisUtil redisUtil = new RedisUtil();
                Object userRedis =   redisUtil.getObject(token);
                if(StringUtil.isEmpty(userRedis)){
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
        }
        User user = userService.retrieveByUserId(userId);
        if(user==null){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        if(StringUtil.isEmpty(user.getIdCard())){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        if(StringUtil.isEmpty(user.getBankIdCard())){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        Boolean flag  = verityService.selectPayAccountByUserId(userId);
        if(flag){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /*认证接口*/
    @RequestMapping(method = {RequestMethod.POST})
    public Result vetify(@RequestParam(value = "user_id", required = false)Integer userId,
                         @RequestParam(value = "bank_code", required = false)String bankCode,
                         @RequestParam(value = "bank_idcard", required = false)String bankIdcard,
                         @RequestParam(value = "bank_phone", required = false)String bankPhone,
                         @RequestParam(value = "idcard", required = false)String idcard,
                         @RequestParam(value = "realname", required = false)String realname,
                         @RequestParam(value = "password", required = false)String password,
                         @RequestParam(value = "token", required = false)String token) {

        try {

            //判断有无token
            if(!StringUtil.isEmpty(token)){
                //判断token是否有效
                RedisUtil redisUtil = new RedisUtil();
                Object userRedis =   redisUtil.getObject(token);
                if(StringUtil.isEmpty(userRedis)){
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
            }
            if(ValidatorUtil.isMobile(bankPhone)){

            }
            if(ValidatorUtil.isPassword(password)){

            }
            if(ValidatorUtil.isIDCard(idcard)){

            }
            if(ValidatorUtil.isUsername(realname)){

            }
            Integer result = verityService.verityUser(userId,bankCode,bankIdcard,bankPhone,idcard,realname,password);
            return  null;
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    }

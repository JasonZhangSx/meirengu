package com.meirengu.uc.controller;

import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.IdentityCardModel;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private RedisClient redisClient;
    //验证实名绑定卡到哪一阶段了
    @RequestMapping(value = "test",method = {RequestMethod.POST})
    public Result update(@RequestParam(value = "user_id", required = false)Integer userId,
                         @RequestParam(value = "token", required = false)String token) {
        //判断有无token
        if(!StringUtil.isEmpty(token)){
            //判断token是否有效
            if(!redisClient.existsObject(token)){
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
    public Result vetify(@RequestParam(value = "user_id", required = true)Integer userId,
                         @RequestParam(value = "bank_code", required = true)String bankCode,
                         @RequestParam(value = "bank_idcard", required = true)String bankIdcard,
                         @RequestParam(value = "bank_phone", required = true)String bankPhone,
                         @RequestParam(value = "idcard", required = true)String idcard,
                         @RequestParam(value = "realname", required = true)String realname,
                         @RequestParam(value = "password", required = false)String password,
                         @RequestParam(value = "invest_conditions", required = false)Integer investConditions,
                         @RequestParam(value = "token", required = true)String token) {

        try {
            //判断token是否有效
            if(!redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            if(StringUtil.isEmpty(bankPhone) || ! ValidatorUtil.isMobile(bankPhone)){
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }

            if(StringUtil.isEmpty(idcard) || ! "".equals(IdentityCardModel.IDCardValidate(idcard))){
                return super.setResult(StatusCode.ID_CARD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.ID_CARD_IS_ERROR));
            }
            if(userService.getBankIdCard(bankIdcard)){
                return super.setResult(StatusCode.BANK_ID_CARD_IS_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.BANK_ID_CARD_IS_EXITS));
            }
            Integer times = 0;
            if(redisClient.existsObject("verify_"+userId)){
                times = (Integer) redisClient.getObject("verify_"+userId);
                if(times>Integer.parseInt(ConfigUtil.getConfig("VERIFY_TIMES"))){
                    return super.setResult(StatusCode.VETIFY_IS_NOT_ALLOWED, null, StatusCode.codeMsgMap.get(StatusCode.VETIFY_IS_NOT_ALLOWED));
                }
                redisClient.setObject("verify_"+userId,times+1);
            }else{
                redisClient.setObject("verify_"+userId,times+1,86400);
            }
            return verityService.verityUser(userId,bankCode,bankIdcard,bankPhone,idcard,realname,password,investConditions);
        }catch (Exception e){
            logger.info("VerifyController.vetify throws Exception:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @RequestMapping(value = "user",method = {RequestMethod.POST})
    public Result user(@RequestParam(value = "user_id", required = true)Integer userId,
                         @RequestParam(value = "id_card", required = false)String idcard,
                         @RequestParam(value = "realname", required = false)String realname){

        try {
            User user = userService.retrieveByUserId(userId);
            if(user!=null && user.getIdCard().equals(idcard)){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.ID_CARD_IS_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.ID_CARD_IS_NOT_EXITS));
            }

        }catch (Exception e){
            logger.info("VerifyController user Throws Exception :{}" ,e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }


    /**
     * 注册验证
     * @param phone
     * @param invitePhone
     * @return
     */
    @RequestMapping(value = "register",method = {RequestMethod.POST})
    public Result user(@RequestParam(value = "phone", required = true)String phone,
                       @RequestParam(value = "invite_phone", required = false)String invitePhone){

        try {

            //手机注册校验
            if (StringUtils.isEmpty(phone) || !ValidatorUtil.isMobile(phone)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }else {
                //验证手机号是否注册
                User user = userService.retrieveByPhone(phone);
                if(user != null){
                    return super.setResult(StatusCode.USER_IS_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_IS_EXITS));
                }
            }

            //邀请注册校验
            if(!StringUtils.isEmpty(invitePhone)){
                //邀请注册参数校验
                if (!ValidatorUtil.isMobile(invitePhone)) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
                }else{
                    //查看邀请人手机号是否注册
                    User userInviter = userService.retrieveByPhone(invitePhone);
                    if(userInviter == null){
                        return super.setResult(StatusCode.USER_INVITER_IS_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_INVITER_IS_NOT_EXITS));
                    }
                }
            }
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info("VerifyController user Throws Exception :{}" ,e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }


}

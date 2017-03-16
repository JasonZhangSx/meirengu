package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.utils.RedisUtil;
import com.meirengu.uc.vo.UserVO;
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

import java.util.Date;

/**
 * 会员控制类
 *
 * @author Marvin
 * @create 2017-01-10 下午7:10
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired    UserService userService;
    @Autowired
    CheckCodeService checkCodeService;

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateUserInfo(UserVO userVO) {

        if(!StringUtil.isEmpty(userVO.getToken())){
            RedisUtil redisUtil = new RedisUtil();
            boolean b = redisUtil.existsObject(userVO.getToken());
            if(b){
                if (StringUtils.isEmpty(userVO.getPhone()) || !ValidatorUtil.isMobile(userVO.getPhone())) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                            .MOBILE_FORMAT_ERROR));
                }
                if (StringUtils.isEmpty(userVO.getEmail()) || !ValidatorUtil.isEmail(userVO.getEmail())) {
                    return super.setResult(StatusCode.EMAIL_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                            .EMAIL_FORMAT_ERROR));
                }
                int result = userService.updateUserInfo(userVO);
                logger.error("UserController.updateUserInfo result << {}, result:{}", result);

                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                        .OK));

            }
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                .INTERNAL_SERVER_ERROR));
    }

    @RequestMapping(value = "password/retrieve",method = RequestMethod.POST)
    public Result retrievePassword(@RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "check_code", required = true) String checkCode,
                                   @RequestParam(value = "old_password", required = true) String oldPassword,
                                   @RequestParam(value = "new_password", required = true) String newPassword
                                   ) {
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if (oldPassword == null) {
            return super.setResult(StatusCode.PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get
                    (StatusCode.PASSWORD_IS_ERROR));
        }
         if (newPassword == null) {
            return super.setResult(StatusCode.PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get
                    (StatusCode.PASSWORD_IS_ERROR));
        }
        //验证手机号是否注册
        User user = userService.retrieveByPhone(mobile);
        if(StringUtil.isEmpty(user)){
            return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
        }
        //验证验证码是否有效
        CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(checkCode));
        if (code == null) {
            return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_INVALID));
        }
        if (code.getExpireTime().compareTo(new Date()) < 0) {
            return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_EXPIRE));
        }
        User usr = userService.verifyByPasswordAndPhone(mobile,oldPassword);
        if(usr == null || StringUtil.isEmpty(usr.getUserId())){
            return super.setResult(StatusCode.OLD_PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .OLD_PASSWORD_IS_ERROR));
        }
        usr.setPassword(newPassword);
        int result = userService.update(usr);
        if(result != 0){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                    .OK));
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                .INTERNAL_SERVER_ERROR));
    }

    @RequestMapping(value = "password/modify",method = RequestMethod.POST)
    public Result modifyPassword(@RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "check_code", required = true) String checkCode,
                                   @RequestParam(value = "new_password", required = true) String newPassword,
                                   @RequestParam(value = "token", required = true) String token) {
        //判断token是否有效
        try{
            RedisUtil redisUtil = new RedisUtil();
            Object userRedis =   redisUtil.getObject(token);
            if(!StringUtil.isEmpty(userRedis)){
                //verify params
                if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                            .MOBILE_FORMAT_ERROR));
                }
                if (newPassword == null) {
                    return super.setResult(StatusCode.PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get
                            (StatusCode.PASSWORD_IS_ERROR));
                }
                //验证手机号是否注册
                User user = userService.retrieveByPhone(mobile);
                if(StringUtil.isEmpty(user)){
                    return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
                }
                //验证验证码是否有效
                CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(checkCode));
                if (code == null) {
                    return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode
                            .CAPTCHA_INVALID));
                }
                if (code.getExpireTime().compareTo(new Date()) < 0) {
                    return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode
                            .CAPTCHA_EXPIRE));
                }
                User usr = new User();
                usr.setPhone(mobile);
                usr.setPassword(newPassword);
                int result = userService.updatePasswordByPhone(usr);
                if(result != 0){
                    return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                            .OK));
                }
            }else{
                //无效token返回登陆
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
        }
        return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
    }

}

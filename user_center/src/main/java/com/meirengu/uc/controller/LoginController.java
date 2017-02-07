package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.utils.ConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.meirengu.uc.utils.HttpUtil.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录控制类
 *
 * @author Marvin
 * @create 2017-01-12 下午12:37
 */
@RestController
public class LoginController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam(required = true) String apikey, @RequestParam(required = true) String phone, @RequestParam(required = true) String captcha, @RequestParam(required = true) int from, @RequestParam(required = true) String ip){
        logger.info("LoginController.login params >> apikey:{}, phone:{},captcha:{}, from:{}, ip:{}", new Object[]{apikey,phone,captcha, from, ip});
        //verify params
        if (!ConfigUtil.getConfig("API_KEY_MEIRENGU").equals(apikey)){
            return super.setReturnMsg(StatusCode.BAD_API_KEY, null, StatusCode.getErrorMsg(StatusCode.BAD_API_KEY));
        }
        if (StringUtils.isEmpty(phone)){
            return super.setReturnMsg(StatusCode.MISSING_ARGUMENT, null, StatusCode.getErrorMsg(StatusCode.MISSING_ARGUMENT));
        }
        if (!StringUtils.isNumeric(phone)){
            return super.setReturnMsg(StatusCode.INVALID_ARGUMENT, null, StatusCode.getErrorMsg(StatusCode.INVALID_ARGUMENT));
        }
        HttpResult hr = loginService.captchaLoginValidate(apikey,phone,Integer.valueOf(captcha));
        if (hr != null){
            if (hr.getStatusCode() == 200){
                logger.warn("loginService.captchaLoginValidate http result >> code:{},content:{}", new Object[] { hr.getStatusCode(), hr.getContent() });
                User user = userService.retrieveByPhone(phone);
                if (user == null){
                    //auto register
                    user = new User();
                    user.setPhone(phone);
                    user.setRegisterFrom(from);
                    user.setLoginIp(ip);
                    user.setLastLoginIp(ip);
                    user.setLoginFrom(from);
                    user.setLoginNum(1);
                    int result = userService.create(user);
                    logger.error("userService.create result << phone:{}, result:{}", new Object[]{phone, result});
                    if (result > 0){
                        return super.setReturnMsg(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));
                    }else{
                        logger.error("userService.create error, try create again >> phone:{}, result:{}", new Object[]{phone, result});
                        userService.create(user);
                    }

                }else {
                    return super.setReturnMsg(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));
                }
            }else if(hr.getStatusCode() == StatusCode.RECORD_NOT_EXISTED){
                return super.setReturnMsg(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }else if (hr.getStatusCode() == StatusCode.CAPTCHA_EXPIRE){
                return super.setReturnMsg(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_EXPIRE));
            }else {
                return super.setReturnMsg(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }
        return super.setReturnMsg(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
    }
}

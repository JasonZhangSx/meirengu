package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.service.UserService;
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
 * 登录控制类
 *
 * @author Marvin
 * @create 2017-01-12 下午12:37
 */
@RestController
public class LoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    CheckCodeService checkCodeService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestParam(value = "mobile", required = true) String mobile,
                        @RequestParam(value = "check_code", required = false) Integer checkCode,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "from", required = true) Integer from,
                        @RequestParam(value = "ip", required = true) String ip) {
        logger.info("LoginController.login params >> mobile:{}, checkCode:{}, password:{}, from:{}, ip:{}", new
                Object[]{mobile, checkCode, password, from, ip});
        //verify params
        if (!ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.getErrorMsg(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if (checkCode != null && password == null){
            //手机动态密码方式登录
            CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(checkCode));
            if (code == null) {
                return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode
                        .CAPTCHA_INVALID));
            }
            if (code.getExpireTime().compareTo(new Date()) < 0) {
                return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode
                        .CAPTCHA_EXPIRE));
            }

        }
        if (checkCode == null && checkCode != null){
            //手机密码方式登录TO-DO
        }
        User user = userService.retrieveByPhone(mobile);
        if (user == null) {
            //auto register
            user = new User();
            user.setPhone(mobile);
            user.setRegisterFrom(from);
            user.setLoginIp(ip);
            user.setLastLoginIp(ip);
            user.setLoginFrom(from);
            user.setLoginNum(1);
            int result = userService.create(user);
            if (result < 0) {
                //try again
                result = userService.create(user);
            }
            logger.info("userService.create result << mobile:{}, result:{}", new Object[]{mobile, result});
        }
        //login
        return super.setResult(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));
    }


}

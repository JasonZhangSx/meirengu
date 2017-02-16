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
    public Result login(@RequestParam(required = true) String mobile,
                        @RequestParam(required = true) String check_code,
                        @RequestParam(required = true) int from,
                        @RequestParam(required = true) String ip) {
        logger.info("LoginController.login params >> mobile:{},checkCode:{}, from:{}, ip:{}", new Object[]{mobile,
                check_code, from, ip});
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.INVALID_ARGUMENT, mobile, StatusCode.getErrorMsg(StatusCode
                    .INVALID_ARGUMENT));
        }
        if (StringUtils.isEmpty(check_code) || !StringUtils.isNumeric(check_code)) {
            return super.setResult(StatusCode.CAPTCHA_INVALID, check_code, StatusCode.getErrorMsg(StatusCode
                    .CAPTCHA_INVALID));
        }
        CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(check_code));
        if (code == null) {
            return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_INVALID));
        }
        if (code.getExpireTime().compareTo(new Date()) < 0) {
            return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_EXPIRE));
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
            logger.error("userService.create result << mobile:{}, result:{}", new Object[]{mobile, result});
        }
        //login
        return super.setResult(StatusCode.OK, user, StatusCode.codeMsgMap.get(StatusCode.OK));
    }
}

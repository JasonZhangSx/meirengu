package com.meirengu.uc.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.model.User;
import com.meirengu.uc.po.RegisterPO;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.utils.ObjectUtils;
import com.meirengu.uc.utils.RedisUtil;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.UuidUtils;
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

    /**
     * 用户登陆接口
     * @param mobile
     * @param wxOpenId
     * @param qqOpenId
     * @param sinaOpenId
     * @param token
     * @param checkCode
     * @param password
     * @param from
     * @param ip
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestParam(value = "mobile", required = false) String mobile,
                        @RequestParam(value = "wx_openid", required = false) String wxOpenId,
                        @RequestParam(value = "qq_openid", required = false) String qqOpenId,
                        @RequestParam(value = "sina_openid", required = false) String sinaOpenId,
                        @RequestParam(value = "token", required = false) String token,
                        @RequestParam(value = "check_code", required = false) Integer checkCode,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "from", required = true) Integer from,
                        @RequestParam(value = "ip", required = true) String ip) {
        logger.info("LoginController.login params >> mobile:{}, checkCode:{}, password:{}, from:{}, ip:{}", new
                Object[]{mobile, checkCode, password, from, ip});
        //判断有无token
        if(!StringUtil.isEmpty(token)){
            //判断token是否有效
            try{
                RedisUtil redisUtil = new RedisUtil();
                Object userRedis =   redisUtil.getObject(token);
                if(!StringUtil.isEmpty(userRedis)){
                    //获取新的token
                    RegisterPO registerPO = loginService.getNewToken(token,userRedis);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    //无效token返回登陆
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
            }catch (Exception e){
                logger.info("LoginController.redis get token result:{}",e.getMessage());
            }
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }else{
            //没有token 判断是否有openId
            if(!StringUtil.isEmpty(wxOpenId)){

            }
            if(!StringUtil.isEmpty(qqOpenId)){

            }
            if(!StringUtil.isEmpty(sinaOpenId)){

            }
            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                        .MOBILE_FORMAT_ERROR));
            }
            if (checkCode == null && password == null) {
                return super.setResult(StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY, null, StatusCode.codeMsgMap.get
                        (StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY));
            }
            User user = userService.retrieveByPhone(mobile);
            if(user==null || StringUtil.isEmpty(user.getUserId())){
                try {
                    //用户为空则注册一个
                    String mobileInviter = "";
                    User usr = this.createUserInfo(mobile,password,from,ip,mobileInviter);
                    RegisterPO registerPO = loginService.setUserToRedis(usr);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
                }catch (Exception e){
                    logger.info(e.getMessage());
                    return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
                }
            }
            if(!StringUtil.isEmpty(checkCode)&&!StringUtil.isEmpty(mobile)){
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
                code.setUse(true);
                code.setUsingTime(new Date());
                int updateResult = checkCodeService.update(code);
                logger.info("LoginController.login update code result:{}", updateResult);
                this.updateUserInfo(user,mobile,ip,from);
                RegisterPO registerPO = loginService.setUserToRedis(user);
                return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class),StatusCode.codeMsgMap.get(StatusCode.OK));
            }
            if(!StringUtil.isEmpty(password)&&!StringUtil.isEmpty(mobile)){
                //todo 手机密码方式登录TO-DO
                User usr = userService.verifyByPasswordAndPhone(mobile,password);
                if(usr != null){
                    this.updateUserInfo(user, mobile, ip, from);
                    RegisterPO registerPO = loginService.setUserToRedis(user);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return super.setResult(StatusCode.PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode                            .PASSWORD_IS_ERROR));
                }
            }
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
    }


    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Result logout(){
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /**
     * 注册
     * @param mobile
     * @param password
     * @param checkCode
     * @param from
     * @param ip
     * @param mobileInviter
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestParam(value = "mobile", required = true) String mobile,
                        @RequestParam(value = "password", required = true) String password,
                        @RequestParam(value = "check_code", required = true) Integer checkCode,
                        @RequestParam(value = "from", required = true) Integer from,
                        @RequestParam(value = "ip", required = true) String ip,
                        @RequestParam(value = "mobile_inviter", required = false) String mobileInviter){//inviter_phone
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if (StringUtils.isEmpty(mobileInviter) || !ValidatorUtil.isMobile(mobileInviter)) {
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if (checkCode == null) {
            return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_INVALID));
        }
        if (password == null) {
            return super.setResult(StatusCode.PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get
                    (StatusCode.PASSWORD_IS_ERROR));
        }
        //验证手机号是否注册
        User user = userService.retrieveByPhone(mobile);
        if(!StringUtil.isEmpty(user)){
            return super.setResult(StatusCode.USER_IS_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_IS_EXITS));
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
        try {
            User usr = this.createUserInfo(mobile,password,from,ip,mobileInviter);
            RegisterPO registerPO = new RegisterPO();
            registerPO.setUser(usr);
            String token = UuidUtils.getUuid();
            RedisUtil redisUtil = new RedisUtil();
            redisUtil.setObject(token,usr);
            registerPO.setToken(token);
            return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info(e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    public int updateUserInfo(User user,String mobile,String ip,int from){

        if(StringUtil.isEmpty(user.getLastLoginIp())){
            user.setLastLoginIp(ip);
            user.setLoginIp(ip);
        }else{
            user.setLastLoginIp(user.getLoginIp());
            user.setLoginIp(ip);
        }
        if(StringUtil.isEmpty(user.getLastLoginTime())){
            user.setLastLoginTime(new Date());
            user.setLoginTime(new Date());
        }else{
            user.setLastLoginTime(user.getLoginTime());
            user.setLoginTime(new Date());
        }
        user.setLoginTime(new Date());
        user.setLoginFrom(from);
        user.setLoginNum(user.getLoginNum()+1);
        return userService.update(user);
    }

    public User createUserInfo(String mobile,String password,int from,String ip,String mobileInviter){
        //创建用户
        User user = new User();
        user.setUserId(UuidUtils.getShortUuid());
        user.setLoginFrom(from);
        user.setLastLoginTime(new Date());
        user.setLoginIp(ip);
        user.setLastLoginIp(ip);
        user.setPassword(password);
        user.setPhone(mobile);
        user.setMobileInviter(mobileInviter);
        user.setLoginNum(1);
        user.setAuth(true);
        user.setAllowInform(true);
        user.setAllowTalk(true);
        user.setState(true);
        user.setBuy(true);
        int result = userService.create(user);
        if(result ==0){
            user.setUserId(UuidUtils.getShortUuid());
            userService.create(user);
        }
        return user;
    }

    public String getToken(User user){
        try {
            String token = UuidUtils.getUuid();
            RedisUtil redisUtil = new RedisUtil();
            redisUtil.setObject(token,user);
            return token;
        }catch (Exception e){
            logger.info("LoginController.login getToken failed:{}",e.getMessage());
            return "";
        }
    }
}

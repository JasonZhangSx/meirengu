package com.meirengu.uc.controller;

import com.meirengu.common.PasswordEncryption;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.model.User;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.vo.response.RegisterInfo;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.service.UserService;
import com.meirengu.utils.ObjectUtils;
import com.meirengu.uc.vo.request.RegisterVO;
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
 * 登录控制类
 * 登陆、注册、退出接口
 * @author huoyan403
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
    @Autowired
    private RedisClient redisClient;
    /**
     * 用户登陆接口
     * @param mobile
     * @param openId
     * @param token
     * @param checkCode
     * @param password
     * @param from
     * @param ip
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestParam(value = "mobile", required = false) String mobile,
                        @RequestParam(value = "openid", required = false) String openId,
                        @RequestParam(value = "token", required = false) String token,
                        @RequestParam(value = "avatar", required = false) String avatar,//用户头像
                        @RequestParam(value = "check_code", required = false) Integer checkCode,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "from", required = true) Integer from,
                        @RequestParam(value = "ip", required = true) String ip,
                        @RequestParam(value = "device_id", required = false,defaultValue = "") String deviceId) {
        logger.info("LoginController.login params >> mobile:{}, checkCode:{}, password:{}, from:{}, ip:{} deviceId:{} time :{}", new
                Object[]{mobile, checkCode, password, from, ip,deviceId,new Date()});
        try{
            //token自动登陆
            if(!StringUtil.isEmpty(token)){
                //判断token是否有效
                if(redisClient.existsObject(token)){
                    //获取新的token
                    RegisterInfo registerInfo = loginService.getNewToken(token,redisClient.getObject(token));
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    //无效token返回登陆
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
            }
            //第三方登录
            //如果有该用户直接登陆  没有的话返回code 去注册页面
            if(!StringUtil.isEmpty(openId)){
                User user = userService.retrieveByOpenId(openId);
                if(user == null){
                    return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
                }else{
                    if(userIsLock(user)){
                        return super.setResult(StatusCode.USER_IS_LOCKED, null, StatusCode.codeMsgMap.get(StatusCode.USER_IS_LOCKED));
                    }
                    userService.updateUserInfo(user, mobile, ip, from);
                    RegisterInfo registerInfo = loginService.setUserToRedis(user,deviceId);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }
            }

            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }
            if (checkCode == null && password == null) {
                return super.setResult(StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY));
            }
            //判断用户是否锁定
            if(!StringUtil.isEmpty(mobile)){
                User user = userService.selectByPhone(mobile);
                if(userIsLock(user)){
                    return super.setResult(StatusCode.USER_IS_LOCKED, null, StatusCode.codeMsgMap.get(StatusCode.USER_IS_LOCKED));
                }
            }
            //密码登陆
            if(!StringUtil.isEmpty(password)&&!StringUtil.isEmpty(mobile)){
                Integer times = 1;
                if(redisClient.existsObject(mobile+"_login_times")){
                    times = Integer.parseInt(String.valueOf(redisClient.getObject(mobile+"_login_times")))+1;
                    if(times > Integer.parseInt(ConfigUtil.getConfig("VERIFY_PASSWORD_TIMES"))){
                        return super.setResult(StatusCode.USER_IS_LOCKED, null,StatusCode.codeMsgMap.get(StatusCode.USER_IS_LOCKED));
                    }
                }
                redisClient.setObject(mobile+"_login_times",String.valueOf(times),0);

                User user = userService.retrieveByPhone(mobile);
                if(user != null && validatePassword(password,user)){
                    userService.updateUserInfo(user, mobile, ip, from);
                    RegisterInfo registerInfo = loginService.setUserToRedis(user,deviceId);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    return super.setResult(StatusCode.INVALID_USERNAME_OR_PASSWORD, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_USERNAME_OR_PASSWORD));
                }
            }

            //验证码登陆
            if(!StringUtil.isEmpty(checkCode)&&!StringUtil.isEmpty(mobile)){

                CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(checkCode));
                if (code == null) {
                    return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_INVALID));
                }
                if (code.getExpireTime().compareTo(new Date()) < 0) {
                    return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_EXPIRE));
                }
                code.setUse(true);
                code.setUsingTime(new Date());
                int updateResult = checkCodeService.update(code);
                logger.info("LoginController.login update code result:{}", updateResult);

                //如果没有注册 给用户注册一个用户
                User user = userService.retrieveByPhone(mobile);
                if(user==null || StringUtil.isEmpty(user.getUserId())){
                    try {
                        User usr = userService.createUserInfo(mobile,from,ip,avatar);
                        if (usr != null){
                            RegisterInfo registerInfo = loginService.setUserToRedis(usr,deviceId);
                            return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class), StatusCode.codeMsgMap.get(StatusCode.OK));
                        }else {
                            return super.setResult(StatusCode.REGISTER_IS_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.REGISTER_IS_FAILED));
                        }
                    }catch (Exception e){
                        logger.error("LoginController login createUser throws Exception :{}",e.getMessage());
                        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
                    }
                }else{//如果用户存在
                    //手机动态密码方式登录
                    userService.updateUserInfo(user,mobile,ip,from);
                    RegisterInfo registerInfo = loginService.setUserToRedis(user,deviceId);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }
            }else{
                return super.setResult(StatusCode.LOGIN_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.LOGIN_FAILED));
            }
        }catch (Exception e){
            logger.error("LoginController.login throws Exception:{}",e.getMessage());
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
    }

    /**
     * 注册
     * @param registerVO
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(RegisterVO registerVO,
                           @RequestParam(value = "device_id", required = false) String deviceId){
        logger.info("LoginController.register params >> registerVO:{} device_id :{} time:{}",registerVO.toString(),deviceId,new Date());
        try {
            if (StringUtils.isEmpty(registerVO.getMobile()) || !ValidatorUtil.isMobile(registerVO.getMobile())) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }
            //邀请注册校验
            if(!StringUtils.isEmpty(registerVO.getMobile_inviter())){
                //邀请注册参数校验
                if (!ValidatorUtil.isMobile(registerVO.getMobile_inviter())) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
                }else{
                    //查看邀请人手机号是否注册
                    User userInviter = userService.retrieveByPhone(registerVO.getMobile_inviter());
                    if(userInviter == null){
                        return super.setResult(StatusCode.USER_INVITER_IS_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_INVITER_IS_NOT_EXITS));
                    }
                }
            }

            //手机密码注册校验
            if(!StringUtil.isEmpty(registerVO.getCheck_code())){
                //验证验证码注册 功能：校验验证码有效性  处理验证码已使用
                CheckCode code = checkCodeService.retrieve(registerVO.getMobile(), Integer.valueOf(registerVO.getCheck_code()));
                if (code == null) {
                    return super.setResult(StatusCode.CAPTCHA_INVALID,null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_INVALID));
                }
                if (code.getExpireTime().compareTo(new Date()) < 0) {
                    return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_EXPIRE));
                }
                code.setUse(true);
                code.setUsingTime(new Date());
                int updateResult = checkCodeService.update(code);
                logger.info("LoginController.register update code result:{}", updateResult);
            }
//            else{
//                return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_INVALID));
//            }

            //验证手机号是否注册
            User user = userService.retrieveByPhone(registerVO.getMobile());
            if(user != null){
                //第三方重新绑定账号 只需更改用户表信息 然后返回登陆
                if(StringUtil.isEmpty(registerVO.getSina_openid()) && StringUtil.isEmpty(registerVO.getWx_openid()) && StringUtil.isEmpty(registerVO.getQq_openid())){
                    return super.setResult(StatusCode.USER_IS_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_IS_EXITS));
                }
                int result = userService.bundThirdParty(registerVO);
                if (result == 1){
                    User userInfo = userService.retrieveByPhone(registerVO.getMobile());
                    RegisterInfo registerInfo = loginService.setUserToRedis(userInfo,deviceId);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class), StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    return super.setResult(StatusCode.REGISTER_IS_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.REGISTER_IS_FAILED));
                }
            }else{
                //注册
                User usr = userService.createUserInfo(registerVO);
                if (usr != null){
                    RegisterInfo registerInfo = loginService.setUserToRedis(usr,deviceId);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerInfo,RegisterInfo.class), StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    return super.setResult(StatusCode.REGISTER_IS_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.REGISTER_IS_FAILED));
                }
            }
        }catch (Exception e){
            logger.error("LoginController register throws Exception :{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR,null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    //退出
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Result logout(@RequestParam(value = "token", required = false) String token,
                         @RequestParam(value = "registration_id", required = false) String registrationId){
        try {
            redisClient.delkeyObject(token);
            //清空token
            //清空redis
            //清空推送别名
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch(Exception e){

            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    //用户密码校验
    private Boolean validatePassword(String password,User user){
        try {
            return  PasswordEncryption.validatePassword(password,user.getPassword());
        }catch (Exception e){
            logger.info("PasswordEncryption.validatePassword throws Exception :{}" ,e.getMessage());
            return  false;
        }
    }


    //用户锁定校验
    private Boolean userIsLock(User user){
        Boolean flag = false;
        if(user != null){
            if(user.getState() == 0){
                flag = true;
            }
        }
        return  flag;
    }
}

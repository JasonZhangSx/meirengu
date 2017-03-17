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
import com.meirengu.uc.vo.RegisterVO;
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
                        @RequestParam(value = "wx_info", required = false) String wxInfo,
                        @RequestParam(value = "qq_openid", required = false) String qqOpenId,
                        @RequestParam(value = "qq_info", required = false) String qqInfo,
                        @RequestParam(value = "sina_openid", required = false) String sinaOpenId,
                        @RequestParam(value = "sina_info", required = false) String sinaInfo,
                        @RequestParam(value = "token", required = false) String token,
                        @RequestParam(value = "avatar", required = false) String avatar,//用户头像
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
                    RegisterPO registerPO = new RegisterPO();
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
            }catch (Exception e){
                logger.info("LoginController.redis get token result:{}",e.getMessage());
            }
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.INVALID_ARGUMENT, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }else{
            //没有token 判断是否有openId
            if(!StringUtil.isEmpty(wxOpenId)){
                //如果有该用户直接登陆  没有的话返回code 去注册页面

                //获取第三方用户昵称
            }
            if(!StringUtil.isEmpty(qqOpenId)){

            }
            if(!StringUtil.isEmpty(sinaOpenId)){

            }
            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                RegisterPO registerPO = new RegisterPO();
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                        .MOBILE_FORMAT_ERROR));
            }
            if (checkCode == null && password == null) {
                RegisterPO registerPO = new RegisterPO();
                return super.setResult(StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get
                        (StatusCode.CHECK_CODE_AND_PASSWORD_NOT_EMPTY));
            }
            if(!StringUtil.isEmpty(password)&&!StringUtil.isEmpty(mobile)){
                //todo 手机密码方式登录TO-DO
                User usr = userService.verifyByPasswordAndPhone(mobile,password);
                if(usr != null){
                    userService.updateUserInfo(usr, mobile, ip, from);
                    RegisterPO registerPO = loginService.setUserToRedis(usr);
                    return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class),StatusCode.codeMsgMap.get(StatusCode.OK));
                }else{
                    RegisterPO registerPO = new RegisterPO();
                    return super.setResult(StatusCode.PASSWORD_IS_ERROR, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode                            .PASSWORD_IS_ERROR));
                }
            }
            if(!StringUtil.isEmpty(checkCode)&&!StringUtil.isEmpty(mobile)){
                User user = userService.retrieveByPhone(mobile);
                if(user==null || StringUtil.isEmpty(user.getUserId())){
                    try {
                        //用户为空则注册一个
                        String mobileInviter = "";
                        User usr = userService.createUserInfo(mobile,password,from,ip,mobileInviter,avatar);
                        RegisterPO registerPO = loginService.setUserToRedis(usr);
                        return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
                    }catch (Exception e){
                        logger.info(e.getMessage());
                        RegisterPO registerPO = new RegisterPO();
                        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
                    }
                }
                //手机动态密码方式登录
                CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(checkCode));
                if (code == null) {
                    RegisterPO registerPO = new RegisterPO();
                    return super.setResult(StatusCode.CAPTCHA_INVALID, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                            .CAPTCHA_INVALID));
                }
                if (code.getExpireTime().compareTo(new Date()) < 0) {
                    RegisterPO registerPO = new RegisterPO();
                    return super.setResult(StatusCode.CAPTCHA_EXPIRE, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                            .CAPTCHA_EXPIRE));
                }
                code.setUse(true);
                code.setUsingTime(new Date());
                int updateResult = checkCodeService.update(code);
                logger.info("LoginController.login update code result:{}", updateResult);
                userService.updateUserInfo(user,mobile,ip,from);
                RegisterPO registerPO = loginService.setUserToRedis(user);
                return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class),StatusCode.codeMsgMap.get(StatusCode.OK));
            }
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.INVALID_ARGUMENT, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }
    }


    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Result logout(){
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /**
     * 注册
     * @param registerVO
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(RegisterVO registerVO){//inviter_phone
        //verify params
        if (StringUtils.isEmpty(registerVO.getMobile()) || !ValidatorUtil.isMobile(registerVO.getMobile())) {
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        if(!"".equals(registerVO.getMobile_inviter())){
            RegisterPO registerPO = new RegisterPO();
            if (StringUtils.isEmpty(registerVO.getMobile_inviter()) || !ValidatorUtil.isMobile(registerVO.getMobile_inviter())) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                        .MOBILE_FORMAT_ERROR));
            }
        }
        if (registerVO.getCheck_code() == null) {
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.CAPTCHA_INVALID, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_INVALID));
        }
        if (registerVO.getPassword() == null) {
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.PASSWORD_IS_ERROR, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get
                    (StatusCode.PASSWORD_IS_ERROR));
        }
        //查看邀请人手机号是否注册
        if(!"".equals(registerVO.getMobile_inviter())){
            User userInviter = userService.retrieveByPhone(registerVO.getMobile_inviter());
            if(StringUtil.isEmpty(userInviter)){
                RegisterPO registerPO = new RegisterPO();
                return super.setResult(StatusCode.USER_INVITER_IS_EXITS, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.USER_INVITER_IS_EXITS));
            }
        }
        //验证手机号是否注册
        User user = userService.retrieveByPhone(registerVO.getMobile());
        if(!StringUtil.isEmpty(user)){
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.USER_IS_EXITS, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.USER_IS_EXITS));
        }
        //验证验证码是否有效
        CheckCode code = checkCodeService.retrieve(registerVO.getMobile(), Integer.valueOf(registerVO.getCheck_code()));
        if (code == null) {
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.CAPTCHA_INVALID, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_INVALID));
        }
        if (code.getExpireTime().compareTo(new Date()) < 0) {
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.CAPTCHA_EXPIRE, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_EXPIRE));
        }
        try {
            User usr = userService.createUserInfo(registerVO);
            RegisterPO registerPO = new RegisterPO();
            registerPO.setUser(usr);
            String token = UuidUtils.getUuid();
            RedisUtil redisUtil = new RedisUtil();
            redisUtil.setObject(token,usr);
            registerPO.setToken(token);
            return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info(e.getMessage());
            RegisterPO registerPO = new RegisterPO();
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR,ObjectUtils.getNotNullObject(registerPO,RegisterPO.class), StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
}

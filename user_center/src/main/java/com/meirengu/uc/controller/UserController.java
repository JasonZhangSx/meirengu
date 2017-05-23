package com.meirengu.uc.controller;

import com.meirengu.common.PasswordEncryption;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.common.Constants;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.vo.request.RegisterVO;
import com.meirengu.uc.vo.response.AvatarVO;
import com.meirengu.utils.ApacheBeanUtils;
import com.meirengu.utils.ObjectUtils;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    UserService userService;
    @Autowired
    CheckCodeService checkCodeService;
    @Autowired
    VerityService verityService;
    @Autowired
    private RedisClient redisClient;


    /**邀请人分页查询 erp使用
     * @param pageNum 当前页
     * @param pageSize 每页显示的条数
     * @param sortBy 排序字段
     * @param order 升序/降序
     * @return
     */
    @RequestMapping(value = "inviter",method = {RequestMethod.POST})
    public Result inviter(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value="per_page", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(value="invest_conditions", required = false) Integer investConditions,
                          @RequestParam(value="is_auth", required = false) Integer isAuth,
                          @RequestParam(value="phone", required = false) String phone,
                          @RequestParam(value="invite_phone", required = false) String invitePhone,
                          @RequestParam(value="realname", required = false) String realname,
                          @RequestParam(value="invite_realname", required = false) String inviteRealname,
                          @RequestParam(value="idcard", required = false) String idcard,
                          @RequestParam(value="invite_idcard", required = false) String inviteIdcard,
                          @RequestParam(value="sortby", required = false) String sortBy,
                          @RequestParam(value="order", required = false) String order){
        try {
            logger.info("cms user/inviter  init   phone:{},invitePhone:{},realname:{},inviteRealname:{},idcard:{},inviteIdcard:{}",
                    phone,invitePhone,realname,inviteRealname,idcard,inviteIdcard);
            Map paramMap = new HashMap<String, Object>();
            Page<User> page = super.setPageParams(pageNum,pageSize);
            paramMap.put("investConditions", investConditions);
            paramMap.put("isAuth", isAuth);
            paramMap.put("phone", phone);
            paramMap.put("realname", realname);
            paramMap.put("idcard", idcard);
            paramMap.put("invitePhone", invitePhone);
            paramMap.put("inviteRealname", inviteRealname);
            paramMap.put("inviteIdcard", inviteIdcard);
            paramMap.put("sortBy", sortBy);
            if(order != null){
                if(order.contains("desc") || order.contains("DESC")){
                    paramMap.put("order", "DESC");
                }
                if(order.contains("asc") || order.contains("ASC")){
                    paramMap.put("order", "ASC");
                }
            }
            page = userService.getListByPage(page, paramMap);
            List<Map<String,Object>> list = page.getList();
//            if(list.size() == 1){
//                //获取余额信息
//                userService.getUserRestMoney(list.get(0));
//                //取累计投资额
//                userService.getUserTotalInvestMoney(list.get(0));
//            }

            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserController inviter throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 获取用户详细信息
     * @param token
     * @param phone
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Result getUserInfo(@RequestParam(value="token", required = false) String token,
                              @RequestParam(value="phone", required = false) String phone){
        logger.info("UserController detail params token:{} phone:{}",token,phone);
        try {
           if(StringUtil.isEmpty(phone) && StringUtil.isEmpty(token)){
               return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
           }
           Map map = new HashMap();
           if(!StringUtil.isEmpty(phone)){
               User user = userService.retrieveByPhone(phone);
               map = ApacheBeanUtils.objectToMap(user);
           }else{
               if(!redisClient.existsObject(token)){
                   return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
               }
               User user = (User)redisClient.getObject(token);
               user = userService.retrieveByPhone(user.getPhone());
               map = ApacheBeanUtils.objectToMap(user);
           }
            userService.getUserRestMoney(map);//获取支付账户余额
            userService.getUserTotalInvestMoney(map);//获取累计投资额
            userService.getWithdrawalsAmount(map);//获取体现中金额
            userService.getBankName(map);//获取银行名称
            return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("UserController detail  throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    /**
     * 获取用户信息
     * @param pageNum
     * @param pageSize
     * @param investConditions
     * @param isAuth
     * @param phone
     * @param realname
     * @param idcard
     * @param sortBy
     * @param order
     * @return
     */
    @RequestMapping(value = "list",method = {RequestMethod.POST})
    public Result list(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value="per_page", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value="is_page", required = false,defaultValue = "1") Integer isPage,
                       @RequestParam(value="invest_conditions", required = false) Integer investConditions,
                       @RequestParam(value="is_auth", required = false) Integer isAuth,
                       @RequestParam(value="phone", required = false) String phone,
                       @RequestParam(value="realname", required = false) String realname,
                       @RequestParam(value="idcard", required = false) String idcard,
                       @RequestParam(value="sortby", required = false) String sortBy,
                       @RequestParam(value="order", required = false) String order){
        logger.info("UserController list params investConditions:{} ,realname:{},idcard:{},phone:{}",
                investConditions,realname,idcard,phone);
        try {
            Map paramMap = new HashMap<String, Object>();
            Page<User> page = new Page<User>();
            if(isPage==1){
                page = super.setPageParams(pageNum,pageSize);
                paramMap.put("investConditions", investConditions);
                paramMap.put("isAuth", isAuth);
                paramMap.put("phone", phone);
                paramMap.put("realname", realname);
                paramMap.put("idcard", idcard);
                paramMap.put("sortBy", sortBy);
                if(!StringUtil.isEmpty(order)){
                    if(order.contains("desc") || order.contains("DESC")){
                        paramMap.put("order", "DESC");
                    }
                    if(order.contains("asc") || order.contains("ASC")){
                        paramMap.put("order", "ASC");
                    }
                }
                page = userService.getByPage(page, paramMap);
            }else{
                page = userService.getUserList(page,paramMap);
            }
            List<Map<String,Object>> list = page.getList();
            if(list.size() == 1){
                for(Map map:list){
                    map.remove("password");
                    userService.getUserRestMoney(map);
                    userService.getUserTotalInvestMoney(map);
                    userService.getWithdrawalsAmount(map);
                    userService.getBankName(map);
                }
            }
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserController list throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    /**
     * 用户基本信息修改
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateUserInfo(@RequestParam(value="token", required = false) String token,
                                 @RequestParam(value="user_id", required = false) Integer userId,
                                 @RequestParam(value="nickname", required = false) String nickname,
                                 @RequestParam(value="phone", required = false) String phone,
                                 @RequestParam(value="avatar", required = false) String avatar,
                                 @RequestParam(value="sex", required = false) Integer sex,
                                 @RequestParam(value="birthday", required = false) Date birthday,
                                 @RequestParam(value="email", required = false) String email,
                                 @RequestParam(value="area_id", required = false) Integer areaId
                                 ) {
        logger.info("UserController update params : userId :{},nickname :{},phone :{},avatar :{},sex :{},birthday :{},email :{},areaId:{}"
                ,userId,nickname,phone,avatar,sex,birthday,email,areaId);
        try {
            if(!StringUtil.isEmpty(token) && !redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            if (!StringUtils.isEmpty(phone)) {
                if(!ValidatorUtil.isMobile(phone)) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
                }
            }
            if(!StringUtil.isEmpty(email)){
                if (StringUtils.isEmpty(email) || !ValidatorUtil.isEmail(email)) {
                    return super.setResult(StatusCode.EMAIL_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.EMAIL_FORMAT_ERROR));
                }
            }
            User user = new User();
            user.setUserId(userId);
            user.setNickname(nickname);
            user.setPhone(phone);
            user.setAvatar(avatar);
            user.setSex(sex);
            user.setBirthday(birthday);
            user.setEmail(email);
            user.setAreaId(areaId);
            int result = userService.updateUserInfo(user);
            if(result==1){
                logger.info("UserController.updateUserInfo result << {}, result:{}", result);
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserController update throw exception:", e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 登陆密码找回
     * @param mobile
     * @param checkCode
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "password/retrieve",method = RequestMethod.POST)
    public Result retrievePassword(@RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "check_code", required = true) String checkCode,
                                   @RequestParam(value = "new_password", required = true) String newPassword) {
        try {
            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }
            if (newPassword == null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get(StatusCode.PASSWORD_IS_MALFORMED));
            }
            //验证手机号是否注册
            User user = userService.retrieveByPhone(mobile);
            if(StringUtil.isEmpty(user)){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
            //验证验证码是否有效
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

            User usr = new User();
            usr.setPhone(mobile);
            usr.setPassword(PasswordEncryption.createHash(newPassword));
            int result = userService.updatePasswordByPhone(usr);
            if(result == 1){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserController password/retrieve throw exception:", e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改登陆密码
     * @param mobile
     * @param newPassword
     * @param token
     * @return
     */
    @RequestMapping(value = "password/modify",method = RequestMethod.POST)
    public Result modifyPassword(  @RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "old_password", required = true) String oldPassword,
                                   @RequestParam(value = "new_password", required = true) String newPassword,
                                   @RequestParam(value = "token", required = true) String token) {
        //判断token是否有效
        try{
            if(StringUtil.isEmpty(token) || !redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            if (newPassword == null || oldPassword ==null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get(StatusCode.PASSWORD_IS_MALFORMED));
            }
            //验证手机号是否注册
            User user = userService.retrieveByPhone(mobile);
            if(StringUtil.isEmpty(user)){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
            if (oldPassword == null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get(StatusCode.PASSWORD_IS_MALFORMED));
            }
            User usr = userService.retrieveByPhone(mobile);
            if(usr == null){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
            if(!validatePassword(oldPassword,usr)){
                return super.setResult(StatusCode.OLD_PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.OLD_PASSWORD_IS_ERROR));
            }
            usr.setPassword(PasswordEncryption.createHash(newPassword));
            int result = userService.updatePasswordByPhone(usr);
            if(result == 1){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserController password/modify throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 用户使用动态密码登陆 设置密码接口
     * @param token
     * @param userId
     * @param password
     * @return
     */
    @RequestMapping(value = "password/site" ,method = RequestMethod.POST)
    public Result setPassword (@RequestParam(value = "token", required = true) String token,
                               @RequestParam(value = "user_id", required = true) Integer userId,
                               @RequestParam(value = "password", required = true) String password){
            try{
                if(StringUtil.isEmpty(token) || !redisClient.existsObject(token)){
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
                User user = userService.retrieveByUserId(userId);
                if(user != null){
                    if("".equals(user.getPassword())){
                        user.setPassword(PasswordEncryption.createHash(password));
                        userService.update(user);
                        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                    }else{
                        return super.setResult(StatusCode.USER_PASSWORD_IS_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_PASSWORD_IS_EXITS));
                    }
                }else{
                    return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
                }
            }catch (Exception e){
                logger.error("UserController setPassword throw exception:", e.getMessage());
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
    }

    /**
     * 获取头像
     * @param userIds
     * @return
     */
    @RequestMapping(value = "listUserAvatar", method = RequestMethod.GET)
    public Result listUserAvatar(@RequestParam(value = "user_ids", required = true) String userIds) {
        try {
            List<String> listUserIds = new ArrayList<>();
            String[]  userId = userIds.split(",");
            for (String id :userId){
                listUserIds.add(id);
            }
            List<AvatarVO> user = userService.listUserAvatar(listUserIds);
           return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(user,List.class,AvatarVO.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("UserController listUserAvatar throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public Result UserAuth(@RequestParam(value = "user_id", required = true) Integer userId) {

        try {
            User user = userService.retrieveByUserId(userId);
            if(user ==null){
                return super.setResult(StatusCode.ADDRESS_ID_NOT_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode.ADDRESS_ID_NOT_EMPTY));
            }
            Boolean flag  = verityService.selectPayAccountByUserId(userId);
            Map<String,Object> map = new HashMap<String,Object>();
            if(flag){
                map.put("payPassword",Constants.ONE_STRING);
            }else{
                map.put("payPassword", Constants.ZERO_STRING);
            }
            if(StringUtil.isEmpty(user.getPassword())){
                map.put("loginPassword",Constants.ZERO_STRING);
            }else{
                map.put("loginPassword",Constants.ONE_STRING);
            }
            map.put("isAuth",user.getIsAuth());
            map.put("isBuy",user.getIsBuy());
            map.put("isAllowInform",user.getIsAllowInform());
            map.put("isAllowTalk",user.getIsAllowTalk());
            return super.setResult(StatusCode.OK,map, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("UserController auth throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }


    /**
     * 修改交易密码
     * @param mobile
     * @param newPassword
     * @param token
     * @return
     */
    @RequestMapping(value = "paypassword/modify",method = RequestMethod.POST)
    public Result modifyPayPassword(  @RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "old_password", required = true) String oldPassword,
                                   @RequestParam(value = "new_password", required = false) String newPassword,
                                   @RequestParam(value = "token", required = true) String token) {
        //判断token是否有效
        try{
            if(StringUtil.isEmpty(token) || !redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            if (oldPassword ==null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get(StatusCode.PASSWORD_IS_MALFORMED));
            }
            //验证手机号是否注册
            User usr = userService.retrieveByPhone(mobile);
            if(usr == null){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
            if(usr.getIsAuth()==0){
                return super.setResult(StatusCode.USER_NOT_AUTH, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_AUTH));
            }
            //修改支付密码
            int result = userService.modifyPayPassword(usr.getUserId(),mobile,oldPassword,newPassword);
            if(result == 1){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.MODIFY_PASSWORD_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.MODIFY_PASSWORD_FAILED));
            }
        }catch (Exception e){
            logger.error("UserController paypassword/modify throw exception:", e.getMessage());
            return super.setResult(StatusCode.OLD_PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.OLD_PASSWORD_IS_ERROR));
        }
    }


    /**
     * 交易密码找回
     * @param mobile
     * @param checkCode
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "paypassword/retrieve",method = RequestMethod.POST)
    public Result retrievePayPassword(@RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "check_code", required = false) String checkCode,
                                   @RequestParam(value = "new_password", required = true) String newPassword,
                                   @RequestParam(value = "type", required = true) Integer type) {
        try {
            //verify params
            if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                        .MOBILE_FORMAT_ERROR));
            }
            if (newPassword == null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get
                        (StatusCode.PASSWORD_IS_MALFORMED));
            }
            //验证手机号是否注册
            User user = userService.retrieveByPhone(mobile);
            if(user == null){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
            if(user.getIsAuth()==0){
                return super.setResult(StatusCode.USER_NOT_AUTH, null, StatusCode.codeMsgMap.get(StatusCode
                        .USER_NOT_AUTH));
            }
            if(type==1){
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
                code.setUse(true);
                code.setUsingTime(new Date());
                int updateResult = checkCodeService.update(code);
            }
            int result = userService.setPayPassword(user.getUserId(),newPassword);
            if(result == 1){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.MODIFY_PASSWORD_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.MODIFY_PASSWORD_FAILED));
            }
        }catch (Exception e){
            logger.error("UserController paypassword/retrieve throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 绑定第三方
     * @param registerVO
     * @return
     */
    @RequestMapping(value = "thirdParty/bund", method = RequestMethod.POST)
    public Result register(RegisterVO registerVO){
        logger.info("绑定第三方 ：{}",registerVO.toString());
        if(StringUtil.isEmpty(registerVO.getToken()) || !redisClient.existsObject(registerVO.getToken())){
            return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
        }
        //第三方绑定校验
        if (!StringUtils.isEmpty(registerVO.getWx_openid()) || !StringUtils.isEmpty(registerVO.getQq_openid()) || !StringUtils.isEmpty(registerVO.getSina_openid()) ) {
            User user = new User();
            if(!StringUtil.isEmpty(registerVO.getWx_openid())){
                user = userService.retrieveByOpenId(registerVO.getWx_openid());
            }
            if(!StringUtil.isEmpty(registerVO.getQq_openid())){
                user = userService.retrieveByOpenId(registerVO.getQq_openid());
            }
            if(!StringUtil.isEmpty(registerVO.getSina_openid())){
                user = userService.retrieveByOpenId(registerVO.getSina_openid());
            }
            if(user !=null){
                return super.setResult(StatusCode.THE_THIRD_PARTY_IS_BOUND, null, StatusCode.codeMsgMap.get(StatusCode.THE_THIRD_PARTY_IS_BOUND));
            }
        }
        int result = userService.bundThirdParty(registerVO);
        if (result == 1){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.BUND_IS_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.BUND_IS_FAILED));
        }
    }

    /**
     * 解除绑定
     * @param token
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping(value = "thirdParty/unbund", method = RequestMethod.POST)
    public Result unbund(@RequestParam(value = "token", required = true) String token,
                         @RequestParam(value = "user_id", required = false) String userId,
                         @RequestParam(value = "type", required = true) Integer type){
        try {
            logger.info("解除绑定第三方 token:{} userId:{}  type:{} ",token,userId,type);
            if(StringUtil.isEmpty(token) || !redisClient.existsObject(token)){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            int result = userService.unbund(userId,type);
            if(result == 1){
                return super.setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNBUND_IS_FAILED,null, StatusCode.codeMsgMap.get(StatusCode.UNBUND_IS_FAILED));
            }
        }catch (Exception e){
            logger.error("UserController paypassword/retrieve throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 验证用户是否存在
     * @param userId
     * @return
     */
    @RequestMapping(value = "verifyUser" ,method = RequestMethod.GET)
    public Result verifyUser (@RequestParam(value = "user_id", required = false) Integer userId,
                              @RequestParam(value = "phone", required = false) String phone){
        try {
            if(StringUtil.isEmpty(userId) && StringUtil.isEmpty(phone)){
                return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
            User user = new User();
            if(!StringUtil.isEmpty(userId)){
                user = userService.retrieveByUserId(userId);
            }
            if(!StringUtil.isEmpty(phone)){
                user = userService.retrieveByPhone(phone);
            }
            if(user != null){
                Map<String,Object> map = new HashedMap();
                map.put("mobile",user.getPhone()+"");//加上空字符串 防止为空 json转换异常
                map.put("realname",user.getRealname()+"");
                map.put("idCard",user.getIdCard()+"");
                map.put("bankIdCard",user.getBankIdCard()+"");
                map.put("bankPhone",user.getBankPhone()+"");
                map.put("bankCode",user.getBankCode()+"");
                return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
        }catch (Exception e){
            logger.error("UserController verifyUser throw exception:", e.getMessage());
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    /**
     * 用户权限信息修改
     * @return
     */
    @RequestMapping(value = "state/update", method = RequestMethod.PUT)
    public Result updateUserState(@RequestParam(value = "state", required = false) Integer state,
                                  @RequestParam(value = "user_id", required = false) Integer userId,
                                  @RequestParam(value = "is_auth", required = false) Integer isAuth,
                                  @RequestParam(value = "is_buy", required = false) Integer isBuy,
                                  @RequestParam(value = "is_allow_inform", required = false) Integer isAllowInform,
                                  @RequestParam(value = "is_allow_talk", required = false) Integer isAllowTalk) {
        logger.info("UserController updateUserState userId :{} state :{} isAuth :{} ,isBuy :{} ,isAllowInform :{} ,isAllowTalk :{} ",
                        userId,state,isAuth,isBuy,isAllowInform,isAllowTalk);
        try {
            User user = new User();
            user.setState(state);
            user.setUserId(userId);
            user.setIsAuth(isAuth);
            user.setIsBuy(isBuy);
            user.setIsAllowInform(isAllowInform);
            user.setIsAllowTalk(isAllowTalk);
            int result = userService.updateUserState(user);
            if(result==1){
                logger.info("UserController.updateUserState result << {}, result:{}", result);
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.error("UserController updateUserState throw exception:", e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }
    /**
     * 校验密码方法提取
     * @param password
     * @param user
     * @return
     */
    private Boolean validatePassword(String password,User user){
        try {
            Boolean result = PasswordEncryption.validatePassword(password,user.getPassword());
            return  result;
        }catch (Exception e){
            logger.error("PasswordEncryption.validatePassword throws Exception :{}" ,e.getMessage());
            return  false;
        }
    }
    /**
     * 格式化string类型时间
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

package com.meirengu.uc.controller;

import com.meirengu.common.PasswordEncryption;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.model.User;
import com.meirengu.uc.po.AvatarPO;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.utils.ObjectUtils;
import com.meirengu.uc.vo.LegalizeVO;
import com.meirengu.uc.vo.UserVO;
import com.meirengu.utils.ApacheBeanUtils;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    /**
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
            paramMap.put("order", order);
            page = userService.getListByPage(page, paramMap);
            List<Map<String,Object>> list = page.getList();
            for(Map map:list){
                userService.getUserRestMoney(map);
                userService.getUserTotalInvestMoney(map);
            }
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
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
        try {
            if(!StringUtil.isEmpty(token) && redisClient.existsObject(token)){
                User user = (User)redisClient.getObject(token);
                Map map = ApacheBeanUtils.objectToMap(user);
                userService.getUserRestMoney(map);
                userService.getUserTotalInvestMoney(map);

                userService.getWithdrawalsAmount(map);
                userService.getBankName(map);
                return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(map,Map.class), StatusCode.codeMsgMap.get(StatusCode.OK));
            }else if(!StringUtil.isEmpty(phone)){
                User user = userService.retrieveByPhone(phone);
                Map map = ApacheBeanUtils.objectToMap(user);
                userService.getUserRestMoney(map);
                userService.getUserTotalInvestMoney(map);

                userService.getWithdrawalsAmount(map);
                userService.getBankName(map);
                return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
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
                paramMap.put("order", order);
                page = userService.getByPage(page, paramMap);
            }else{
                page = userService.getUserList(page,paramMap);
            }
            List<Map<String,Object>> list = page.getList();
            for(Map map:list){
                map.remove("password");
                userService.getUserRestMoney(map);
                userService.getUserTotalInvestMoney(map);
                userService.getWithdrawalsAmount(map);
                userService.getBankName(map);
            }
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    /**
     * 用户信息更新
     * @param userVO
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateUserInfo(UserVO userVO) {
        try {
            if(!StringUtil.isEmpty(userVO.getToken()) && !redisClient.existsObject(userVO.getToken())){
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            if (!StringUtils.isEmpty(userVO.getPhone())) {
                if(!ValidatorUtil.isMobile(userVO.getPhone())) {
                    return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                            .MOBILE_FORMAT_ERROR));
                }
            }
            if(!StringUtil.isEmpty(userVO.getEmail())){
                if (StringUtils.isEmpty(userVO.getEmail()) || !ValidatorUtil.isEmail(userVO.getEmail())) {
                    return super.setResult(StatusCode.EMAIL_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                            .EMAIL_FORMAT_ERROR));
                }
            }
            int result = userService.updateUserInfo(userVO);
            if(result==1){
                logger.info("UserController.updateUserInfo result << {}, result:{}", result);
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                .INTERNAL_SERVER_ERROR));
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
                return super.setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                        .MOBILE_FORMAT_ERROR));
            }
            if (newPassword == null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get
                        (StatusCode.PASSWORD_IS_MALFORMED));
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
            code.setUse(true);
            code.setUsingTime(new Date());
            int updateResult = checkCodeService.update(code);

            User usr = new User();
            usr.setPhone(mobile);
            usr.setPassword(PasswordEncryption.createHash(newPassword));
            int result = userService.updatePasswordByPhone(usr);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                        .OK));
            }
        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .INTERNAL_SERVER_ERROR));
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                .INTERNAL_SERVER_ERROR));
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
            if(!redisClient.existsObject(token)){
                //无效token返回登陆
                return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
            }
            if (newPassword == null || oldPassword ==null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get
                        (StatusCode.PASSWORD_IS_MALFORMED));
            }
            //验证手机号是否注册
            User user = userService.retrieveByPhone(mobile);
            if(StringUtil.isEmpty(user)){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
            if (oldPassword == null) {
                return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get
                        (StatusCode.PASSWORD_IS_MALFORMED));
            }
            User usr = userService.retrieveByPhone(mobile);
            if(usr == null){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode
                        .USER_NOT_EXITS));
            }
            if(validatePassword(oldPassword,usr)){
                return super.setResult(StatusCode.OLD_PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                        .OLD_PASSWORD_IS_ERROR));
            }
            usr.setPassword(PasswordEncryption.createHash(newPassword));
            int result = userService.updatePasswordByPhone(usr);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode
                        .OK));
            }
        }catch (Exception e){
            logger.info("LoginController.redis get token result:{}",e.getMessage());
        }
        return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
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
                return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(map,Map.class), StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
            }
        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 用户使用动态密码登陆 设置密码接口
     * @param token
     * @param userId
     * @param password
     * @return
     */
    @RequestMapping(value = "setPassword" ,method = RequestMethod.POST)
    public Result setPassword (@RequestParam(value = "token", required = true) String token,
                               @RequestParam(value = "user_id", required = true) Integer userId,
                               @RequestParam(value = "password", required = true) String password){
        if(!StringUtil.isEmpty(token)){
            //判断token是否有效
            try{
                if(!redisClient.existsObject(token)){
                    //无效token返回登陆
                    return super.setResult(StatusCode.TOKEN_IS_TIMEOUT, null, StatusCode.codeMsgMap.get(StatusCode.TOKEN_IS_TIMEOUT));
                }
                User user = userService.retrieveByUserId(userId);
                if(user != null){
                    if(PasswordEncryption.validatePassword("",user.getPassword())){
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
                logger.info("LoginController.redis get token result:{}",e.getMessage());
            }
        }
        return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
    }

    /**
     * 判断认证进行到哪一步
     * @param legalizeVO
     * @return
     */
    @RequestMapping(value = "legalize", method = RequestMethod.POST)
    public Result legalize(LegalizeVO legalizeVO) {



        return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
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
            List<AvatarPO> user = userService.listUserAvatar(listUserIds);
           return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(user,List.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .INTERNAL_SERVER_ERROR));
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
                map.put("payPassword","1");
            }else{
                map.put("payPassword","0");
            }
            map.put("isAuth",user.getIsAuth());
            map.put("isBuy",user.getIsBuy());
            map.put("isAllowInform",user.getIsAllowInform());
            map.put("isAllowTalk",user.getIsAllowTalk());
            return super.setResult(StatusCode.OK, ObjectUtils.getNotNullObject(map,Map.class), StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .INTERNAL_SERVER_ERROR));
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
            if(redisClient.existsObject(token)){
                if (oldPassword ==null) {
                    return super.setResult(StatusCode.PASSWORD_IS_MALFORMED, null, StatusCode.codeMsgMap.get
                            (StatusCode.PASSWORD_IS_MALFORMED));
                }
                //验证手机号是否注册
                User usr = userService.retrieveByPhone(mobile);
                if(usr == null){
                    return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode
                            .USER_NOT_EXITS));
                }
                //// TODO: 4/1/2017 需增加外部验证oldpassword是否正确 
                int result = userService.modifyPayPassword(usr.getUserId(),mobile,oldPassword,newPassword);
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
        return super.setResult(StatusCode.OLD_PASSWORD_IS_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.OLD_PASSWORD_IS_ERROR));
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
            if(StringUtil.isEmpty(user)){
                return super.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
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
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
        }catch (Exception e){
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .INTERNAL_SERVER_ERROR));
        }
        return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
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
            logger.info("PasswordEncryption.validatePassword throws Exception :{}" ,e.getMessage());
            return  false;
        }
    }
}

package com.meirengu.uc.service.impl;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.PasswordEncryption;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.InviterDao;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.thread.InitInviterThread;
import com.meirengu.uc.thread.InitPayAccountThread;
import com.meirengu.uc.thread.ReceiveCouponsThread;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.ObjectUtils;
import com.meirengu.uc.utils.ThreadPoolSingleton;
import com.meirengu.uc.vo.request.RegisterVO;
import com.meirengu.uc.vo.request.UserVO;
import com.meirengu.uc.vo.response.AvatarVO;
import com.meirengu.utils.*;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * 会员服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:24
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private ExecutorService cachedThreadPool = ThreadPoolSingleton.getInstance();

    @Autowired
    UserDao userDao;
    @Autowired
    InviterDao inviterDao;

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public User create(User user){
        try {
            int result = userDao.create(user);
            if(result == 0){
                user.setUserId(UuidUtils.getShortUuid());
                result = userDao.create(user);
            }
            if(result == 1){
                //初始化支付账户
                cachedThreadPool.execute(new InitPayAccountThread(
                        user.getUserId(),
                        user.getPhone()
                ));
                //领取注册抵扣券
                cachedThreadPool.execute(new ReceiveCouponsThread(
                        user.getUserId(),
                        user.getPhone()
                ));
                //初始化邀请关系
                if(!StringUtil.isEmpty(user.getMobileInviter())){
                    cachedThreadPool.execute(new InitInviterThread(
                            user,
                            userDao,
                            inviterDao
                    ));
                }
                return user;
            }else{
                logger.info("UserServiceImpl createUser failed :{}",user);
                return null;
            }
        }catch (Exception e){
            logger.error("用户初始化失败 phone：{},异常信息为：{}" ,user.getPhone(),e);
            return null;
        }
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public User retrieveByUserId(int userId) {
        return userDao.retrieveByUserId(userId);
    }

    @Override
    public User retrieveByPhone(String phone) {
        return userDao.retrieveByPhone(phone);
    }

    @Override
    public int updateUserInfo(UserVO userVO) {
        User user = new User();
        if(!StringUtil.isEmpty(userVO.getUser_id())){
            user.setUserId(userVO.getUser_id());
        }
        if(!StringUtil.isEmpty(userVO.getNickname())){
            user.setNickname(userVO.getNickname());
        }
        if(!StringUtil.isEmpty(userVO.getAvatar())){
            user.setAvatar(userVO.getAvatar());
        }
        if(!StringUtil.isEmpty(userVO.getPhone())){
            user.setPhone(userVO.getPhone());
        }
        if(!StringUtil.isEmpty(userVO.getRealname())){
            user.setRealname(userVO.getRealname());
        }
        if(!StringUtil.isEmpty(userVO.getEmail())){
            user.setEmail(userVO.getEmail());
        }
        if(!StringUtil.isEmpty(userVO.getBirthday())){
            user.setBirthday(userVO.getBirthday());
        }
        if(!StringUtil.isEmpty(userVO.getSex())){
            user.setSex(userVO.getSex());
        }
        if(!StringUtil.isEmpty(userVO.getQq())){
            user.setQq(userVO.getQq());
        }
        if(!StringUtil.isEmpty(userVO.getQq_openid())){
            user.setQqOpenid(userVO.getQq_openid());
        }
        if(!StringUtil.isEmpty(userVO.getWx())){
            user.setWx(userVO.getWx());
        }
        if(!StringUtil.isEmpty(userVO.getWx_openid())){
            user.setWxOpenid(userVO.getWx_openid());
        }
        if(!StringUtil.isEmpty(userVO.getSina_openid())){
            user.setSinaOpenid(userVO.getSina_openid());
        }
        if(!StringUtil.isEmpty(userVO.getArea_id())){
            user.setAreaId(userVO.getArea_id());
        }
        return userDao.update(user);

    }

    @Override
    public int updatePasswordByPhone(User user) {
        return userDao.updatePasswordByPhone(user);
    }

    @Override
    public int updateUserInfo(User user, String mobile, String ip, Integer from) {
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
        return userDao.update(user);
    }

    @Override
    public User createUserInfo(String mobile,Integer from, String ip,String avatar) {

        //创建用户
        User user = new User();
        if(StringUtil.isEmpty(avatar)){
            String [] avatarDefault = ConfigUtil.getConfig("USER_AVATAR").split(",");
            Integer number = Integer.parseInt(ConfigUtil.getConfig("USER_AVATAR_NUMBER"));
            user.setAvatar(avatarDefault[(int) Math.random()*number]);
        }else{
            user.setAvatar(avatar);
        }
        user.setNickname("MRG_"+mobile.substring(7,11));
        user.setUserId(UuidUtils.getShortUuid());
        user.setLoginFrom(from);
        user.setLastLoginTime(new Date());
        user.setLoginIp(ip);
        user.setLastLoginIp(ip);
        user.setPhone(mobile);
        user.setLoginNum(1);
        user.setIsAuth(0);
        user.setIsAllowInform(1);
        user.setIsAllowTalk(1);
        user.setState(1);
        user.setIsBuy(1);
        user.setRegisterFrom(from);
        user.setRegisterTime(new Date());
        user.setPassword("");
        ObjectUtils.getNotNullObject(user,User.class);
        return this.create(user);
    }

    /**
     * 注册用户创建用户
     * @param registerVO
     * @return
     */
    @Override
    public User createUserInfo(RegisterVO registerVO) {
        //创建用户
        User user = new User();
        if(StringUtil.isEmpty(registerVO.getAvatar())) {
            String [] avatarDefault = ConfigUtil.getConfig("USER_AVATAR").split(",");
            Integer number = Integer.parseInt(ConfigUtil.getConfig("USER_AVATAR_NUMBER"));
            user.setAvatar(avatarDefault[(int) (Math.random()*number)]);
        }else{
            try {
                //上传头像到oss 并保存文件路径到user
                //oss配置信息  从oss读取文件
                String contractFolderName = ConfigUtil.getConfig("contractFolderName");
                String endpoint = ConfigUtil.getConfig("endpoint");
                String accessKeyId = ConfigUtil.getConfig("accessKeyId");
                String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
                String bucketName = ConfigUtil.getConfig("bucketName");
                String callback = ConfigUtil.getConfig("callbackUrl");

                String foldName = ConfigUtil.getConfig("foldName");
                String fileName = new Date().getTime() + new Random().nextInt(10000)+".jpg";
                OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
                fileUtils.uploadUrl(registerVO.getAvatar(),foldName,fileName);

                user.setAvatar(foldName+"/"+fileName);
            } catch (IOException e) {
                logger.error("第三方头像上传失败！");
            }
        }
        user.setUserId(UuidUtils.getShortUuid());
        user.setLoginFrom(registerVO.getFrom());
        user.setLastLoginTime(new Date());
        user.setLoginIp(registerVO.getIp());
        user.setLastLoginIp(registerVO.getIp());
        user.setPhone(registerVO.getMobile());
        user.setMobileInviter(registerVO.getMobile_inviter());
        user.setNickname("MRG_"+registerVO.getMobile().substring(7,11));
        if(!StringUtil.isEmpty(registerVO.getWx_openid())){
            user.setWxOpenid(registerVO.getWx_openid());
            user.setWxInfo(registerVO.getWx_info());
            user.setWx(registerVO.getWx_name());
        }
        if(!StringUtil.isEmpty(registerVO.getQq_openid())) {
            user.setQqOpenid(registerVO.getQq_openid());
            user.setQqInfo(registerVO.getQq_info());
            user.setQq(registerVO.getQq_name());
        }
        if(!StringUtil.isEmpty(registerVO.getSina_openid())) {
            user.setSinaOpenid(registerVO.getSina_openid());
            user.setSinaInfo(registerVO.getSina_info());
            user.setSina(registerVO.getSina_name());
        }
        user.setLoginNum(1);
        user.setRegisterFrom(registerVO.getFrom());
        user.setRegisterTime(new Date());
        user.setIsAuth(0);
        user.setIsAllowInform(1);
        user.setIsAllowTalk(1);
        user.setState(1);
        user.setIsBuy(1);
        try {
            if(!StringUtil.isEmpty(registerVO.getPassword())){
                String password = PasswordEncryption.createHash(registerVO.getPassword());
                user.setPassword(password);
            }else{
                user.setPassword("");
            }
        }catch (Exception e){
            logger.error("UserServiceImpl PasswordEncryption.createHash throws Exception :{}" ,e.getMessage());
        }
        ObjectUtils.getNotNullObject(user,User.class);
        return this.create(user);
    }

    @Override
    public List<AvatarVO> listUserAvatar(List<String> listUserIds) {

        List<User> list =  userDao.listUserAvatar(listUserIds);
        List<AvatarVO> avatarVOs = new ArrayList<>();
        for(User user :list){
            AvatarVO avatarVO = new AvatarVO();
            avatarVO.setUserId(user.getUserId());
            avatarVO.setAvatar(user.getAvatar());
            avatarVOs.add(avatarVO);
        }
        return avatarVOs;
    }

    @Override
    public void getUserRestMoney(Map map) {
        HttpResult hr = null;
        Map<String, Object> paramsmap = new HashMap<String, Object>();
        paramsmap.put("userId",map.get("userId"));
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", JacksonUtil.toJSon(map));
        String url = ConfigUtil.getConfig("URI_GET_USER_PAYACCOUNT");
        String urlAppend = url+"?content="+ URLEncoder.encode(JacksonUtil.toJSon(paramsmap));
        logger.info("UserServiceImpl.send get >> uri :{}, params:{}", new Object[]{urlAppend, params});
        try {
            hr = HttpUtil.doGet(urlAppend);
        } catch (Exception e) {
            logger.error("UserServiceImpl.send error >> params:{}, exception:{}", new Object[]{urlAppend, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            Map<String,Object> account = new HashedMap();
            account = JacksonUtil.readValue(hr.getContent(),Map.class);
            if(account!=null){
                Map mapData = (Map)account.get("data");
                if(mapData!=null){
                    Map accountUser = (Map) mapData.get("account");
                    if(accountUser!=null){
                       map.put("accountBalance",accountUser.get("accountBalance"));
                    }
                }
            }
        }else{
            logger.error("UserServiceImpl.back code >> params:{}, exception:{}");
        }
    }

    @Override
    public void getUserTotalInvestMoney(Map map) {
        map.put("totalInvestMoney","0.00");
        try {
            HttpResult hr = null;
            String url = ConfigUtil.getConfig("URI_GET_SUM_AMOUNT");
            String urlAppend = url+"?user_ids="+ map.get("userId");
            hr = HttpUtil.doGet(urlAppend);
            logger.info("UserServiceImpl.send get >> uri :{}, params:{}", new Object[]{urlAppend});
            if(hr.getStatusCode()== StatusCode.OK){
                Map<String,Object> account = new HashedMap();
                account = JacksonUtil.readValue(hr.getContent(),Map.class);
                if(account!=null){
                    List<Map> mapData = (List<Map>)account.get("data");
                    if(mapData!=null && mapData.get(0) != null){
                        map.put("totalInvestMoney",mapData.get(0).get("sumOrderAmount"));
                        map.put("sumRebateAmount",mapData.get(0).get("sumRebateAmount"));
                        map.put("sumCostAmount",mapData.get(0).get("sumCostAmount"));
                    }
                }
            }else{
                logger.error("UserServiceImpl.back code >> params:{}, exception:{}");
            }
        } catch (Exception e) {
            logger.error("UserServiceImpl.send error >> params:{}, exception:{}", new Object[]{ e});
        }
    }

    @Override
    public void getWithdrawalsAmount(Map map) {
        map.put("withdrawalsAmount","");
        try {
            HttpResult hr = null;
            String url = ConfigUtil.getConfig("URI_GET_WITHDRAWALSAMOUNT");
            String urlAppend = url+"?userId="+ map.get("userId");
            hr = HttpUtil.doGet(urlAppend);
            logger.info("UserServiceImpl.send get >> uri :{}, params:{}", new Object[]{urlAppend});
            if(hr.getStatusCode()== StatusCode.OK){
                Map<String,Object> account = new HashedMap();
                account = JacksonUtil.readValue(hr.getContent(),Map.class);
                if(account!=null){
                    Map mapData = (Map)account.get("data");
                    if(mapData!=null){
                        map.put("withdrawalsAmount",mapData.get("withdrawalsAmount"));
                    }
                }
            }else{
                logger.error("UserServiceImpl.back code >> params:{}, exception:{}");
            }
        } catch (Exception e) {
            logger.error("UserServiceImpl.send error >> params:{}, exception:{}", new Object[]{ e});
        }
    }

    @Override
    public void getBankName(Map map) {
        map.put("bankName","");//防止为空客户端崩溃

        try {
            HttpResult hr = null;
            String url = ConfigUtil.getConfig("URI_GET_CHANNELBANK");
            String urlAppend = url+"?bankCode="+ map.get("bankCode");
            hr = HttpUtil.doGet(urlAppend);
            logger.info("UserServiceImpl.send get >> uri :{}, params:{}", new Object[]{urlAppend});
            if(hr.getStatusCode()== StatusCode.OK){
                Map<String,Object> account = new HashedMap();
                account = JacksonUtil.readValue(hr.getContent(),Map.class);
                if(account!=null){
                    Map mapData = (Map)account.get("data");
                    if(mapData!=null){
                        ArrayList channelBank = (ArrayList) mapData.get("channelBank");
                        if(channelBank.size()!=0){
                            Map channel = (Map)channelBank.get(0);
                            map.put("bankName",channel.get("bankName"));
                        }

                    }
                }
            }else{
                logger.error("UserServiceImpl.back code >> params:{}, exception:{}");
            }
        } catch (Exception e) {
            logger.error("UserServiceImpl.send error >> params:{}, exception:{}", new Object[]{ e});
        }
    }
    @Override
    public Page<User> getByPage(Page<User> page, Map paramMap) {

        int startPos = page.getStartPos();
        int pageSize = page.getPageSize();
        RowBounds rowBounds = new RowBounds(startPos, pageSize);
        List<Map<String, Object>> aList = userDao.getUserByPage(paramMap, rowBounds);
        int totalCount = userDao.getUserCount(paramMap);
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        logger.info(" page params is "+ JSON.toJSON(paramMap));
        return page;
    }

    /**
     * 不分页 封装入page
     * @param page
     * @param paramMap
     * @return
     */
    @Override
    public Page<User> getUserList(Page<User> page, Map paramMap) {

        List<Map<String, Object>> aList = userDao.getUserByPage(paramMap);
        int totalCount = userDao.getUserCount(paramMap);
        page.setPageSize(aList.size());
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        logger.info(" page params is "+ JSON.toJSON(paramMap));
        return page;
    }

    @Override
    public int modifyPayPassword(Integer userId,String mobile, String oldPassword, String newPassword) {
        try {
            Boolean flag = false;
            HttpResult hr = null;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId",userId+"");
            Map<String, String> params = new HashMap<String, String>();
            params.put("content", JacksonUtil.toJSon(map));
            String url = ConfigUtil.getConfig("URI_GET_USER_PAYACCOUNT");
            String urlAppend = url+"?content="+ URLEncoder.encode(JacksonUtil.toJSon(map));
            logger.info("VerityServiceImpl.send get >> uri :{}, params:{}", new Object[]{url, params});
            try {
                hr = HttpUtil.doGet(urlAppend);
            } catch (Exception e) {
                logger.error("VerityServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
            }
            if(hr.getStatusCode() == StatusCode.OK){
                Map<String,Object> account = new HashedMap();
                account = JacksonUtil.readValue(hr.getContent(),Map.class);
                if(account!=null){
                    Map mapData = (Map)account.get("data");
                    if(mapData!=null){
                        Map accountUser = (Map) mapData.get("account");
                        if(accountUser!=null){
                            String password = (String) accountUser.get("password");
                            if(PasswordEncryption.validatePassword(oldPassword,password)){
                                if(!StringUtil.isEmpty(newPassword)){
                                    Map<String, String> payAccount = new HashMap<String, String>();
                                    payAccount.put("password",PasswordEncryption.createHash(newPassword));
                                    payAccount.put("userId",userId+"");
                                    Map<String, String> paramsModify = new HashMap<String, String>();
                                    paramsModify.put("content", JacksonUtil.toJSon(payAccount));
                                    String urlModify = ConfigUtil.getConfig("URI_MODIFY_USER_PAYACCOUNT");
                                    hr = HttpUtil.doPostForm(urlModify,paramsModify);
                                    if(hr.getStatusCode() != StatusCode.OK){
                                        hr = HttpUtil.doPostForm(urlModify,paramsModify);
                                    }else{
                                        return 1;
                                    }
                                }else{
                                    return 1;
                                }
                            }else{
                                return 0;
                            }
                        }
                    }
                }
            }else{
                logger.error("VerityServiceImpl.back code >> params:{}, exception:{}", hr.getStatusCode(),hr.getContent());
            }
        }catch (Exception e){
            logger.error("VerityServiceImpl.back code >> throws exception:{}", e.getMessage());
            return 0;
        }
        return 0;
    }

    @Override
    public int setPayPassword(Integer userId, String newPassword) {

        try {
            HttpResult hr = null;
            Map<String, String> payAccount = new HashMap<String, String>();
            payAccount.put("password",PasswordEncryption.createHash(newPassword));
            payAccount.put("userId",userId+"");
            Map<String, String> paramsModify = new HashMap<String, String>();
            paramsModify.put("content", JacksonUtil.toJSon(payAccount));
            String urlModify = ConfigUtil.getConfig("URI_MODIFY_USER_PAYACCOUNT");
            hr = HttpUtil.doPostForm(urlModify,paramsModify);
            if(hr.getStatusCode() == StatusCode.OK) {
                return 1;
            }else{
                hr = HttpUtil.doPostForm(urlModify, paramsModify);
                if(hr.getStatusCode() == StatusCode.OK){
                    return 1;
                }else{
                    return 0;
                }
            }
        }catch (Exception e){
            logger.info("UserServiceImpl setPayPassword throws Exception :{}",e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean getBankIdCard(String bankIdcard) {
        Boolean flag = false;
        if(userDao.getBankIdCard(bankIdcard)==1){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean getIdCard(String idcard) {
        Boolean flag = false;
        if(userDao.getIdCard(idcard)==1){
            flag = true;
        }
        return flag;
    }

    @Override
    public User retrieveByOpenId(String openId) {
        return userDao.retrieveByOpenId(openId);
    }

    @Override
    public int bundThirdParty(RegisterVO registerVO) {

        User user = new User();
        user.setPhone(registerVO.getMobile());
        if(!StringUtil.isEmpty(registerVO.getWx_openid())){
            user.setWx(registerVO.getWx_name());
            user.setWxOpenid(registerVO.getWx_openid());
            user.setWxInfo(registerVO.getWx_info());
        }
        if(!StringUtil.isEmpty(registerVO.getQq_openid())){
            user.setQq(registerVO.getQq_name());
            user.setQqOpenid(registerVO.getQq_openid());
            user.setQqInfo(registerVO.getQq_info());
        }
        if(!StringUtil.isEmpty(registerVO.getSina_openid())){
            user.setSina(registerVO.getSina_name());
            user.setSinaOpenid(registerVO.getSina_openid());
            user.setSinaInfo(registerVO.getSina_info());
        }
        return userDao.update(user);
    }

    @Override
    public int unbund(String userId, Integer type) {
        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        if(type == 1){
            user.setWxOpenid("");
            user.setWx("");
            user.setWxInfo("");
        }
        if(type == 2){
            user.setQq("");
            user.setQqInfo("");
            user.setQqOpenid("");
        }
        if(type == 3){
            user.setSina("");
            user.setSinaOpenid("");
            user.setSinaInfo("");
        }
        return userDao.update(user);
    }
}

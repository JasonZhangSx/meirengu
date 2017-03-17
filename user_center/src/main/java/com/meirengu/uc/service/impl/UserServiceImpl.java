package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.vo.UserVO;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:24
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public int create(User user) {

        HttpResult hr = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", user.getPhone());
        params.put("userId", user.getUserId()+"");
        params.put("content", JacksonUtil.toJSon(user));
        String url = ConfigUtil.getConfig("URI_INIT_USER_PAYACCOUNT");
        logger.info("UserServiceImpl.initUserPayAccount post >> uri :{}, params:{}", new Object[]{url, params});
        try {
            hr = HttpUtil.doPostForm(url,params);
        } catch (Exception e) {
            logger.error("CheckCodeServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
        }
        if(hr.getStatusCode() == 200){
            int result = userDao.create(user);
            if(result == 1){
                return result;
            }
        }
        return 0;
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
    public User verifyByPasswordAndPhone(String mobile, String password) {
        Map<String,String> map = new HashMap<>();
        map.put("phone",mobile);
        map.put("password",password);
        return userDao.verifyByPasswordAndPhone(map);
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
        if(!StringUtil.isEmpty(userVO.getPassword())){
            user.setPassword(userVO.getPassword());
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
        if(!StringUtil.isEmpty(userVO.isSex())){
            user.setSex(userVO.isSex());
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
    public User createUserInfo(String mobile, String password, Integer from, String ip, String mobileInviter) {

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
        int result = userDao.create(user);
        if(result ==0){
            user.setUserId(UuidUtils.getShortUuid());
            userDao.create(user);
        }
        return user;
    }
}

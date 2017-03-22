package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.po.AvatarPO;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.thread.InitPayAccountThread;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.vo.RegisterVO;
import com.meirengu.uc.vo.UserVO;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 会员服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:24
 */
@Service
public class UserServiceImpl extends Thread implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public int create(User user){

        int result = userDao.create(user);
        Thread t = Thread.currentThread();
        String name = t.getName();
        if(result == 1){
            InitPayAccountThread initPayAccountThread = new InitPayAccountThread(user);
            initPayAccountThread.run();
            return result;
        }else{
            logger.info("UserServiceImpl createUser failed :{}",user);
            return 0;
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

//    @Override
//    public User verifyByPasswordAndPhone(String mobile, String password) {
//        Map<String,String> map = new HashMap<>();
//        map.put("phone",mobile);
//        map.put("password",password);
//        return userDao.verifyByPasswordAndPhone(map);
//    }

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
    public User createUserInfo(String mobile, String password, Integer from, String ip, String mobileInviter,String avatar) {

        //创建用户
        User user = new User();
        if("".equals(avatar)){
            String [] avatarDefault = ConfigUtil.getConfig("USER_AVATAR").split(",");
            Integer number = Integer.parseInt(ConfigUtil.getConfig("USER_AVATAR_NUMBER"));
            user.setAvatar(avatarDefault[(int) Math.random()*number]);
        }else{
            user.setAvatar(avatar);
        }
        user.setUserId(UuidUtils.getShortUuid());
        user.setLoginFrom(from);
        user.setLastLoginTime(new Date());
        user.setLoginIp(ip);
        user.setLastLoginIp(ip);
        user.setPassword(password);
        user.setPhone(mobile);
        user.setMobileInviter(mobileInviter);
        user.setLoginNum(1);
        user.setIsAuth(0);
        user.setIsAllowInform(1);
        user.setIsAllowTalk(1);
        user.setState(1);
        user.setIsBuy(1);
        user.setRegisterFrom(from);
        user.setRegisterTime(new Date());
        int result = this.create(user);
        if(result ==0){
            user.setUserId(UuidUtils.getShortUuid());
            this.create(user);
        }
        return user;
    }

    @Override
    public User createUserInfo(RegisterVO registerVO) {
        //创建用户
        User user = new User();
        if("".equals(registerVO.getAvatar())) {
            String [] avatarDefault = ConfigUtil.getConfig("USER_AVATAR").split(",");
            Integer number = Integer.parseInt(ConfigUtil.getConfig("USER_AVATAR_NUMBER"));
            user.setAvatar(avatarDefault[(int) Math.random()*number]);
        }else{
            user.setAvatar(registerVO.getAvatar());
        }
        user.setUserId(UuidUtils.getShortUuid());
        user.setLoginFrom(registerVO.getFrom());
        user.setLastLoginTime(new Date());
        user.setLoginIp(registerVO.getIp());
        user.setLastLoginIp(registerVO.getIp());
        user.setPassword(registerVO.getPassword());
        user.setPhone(registerVO.getMobile());
        user.setMobileInviter(registerVO.getMobile_inviter());
        user.setNickname("MRG_"+registerVO.getMobile().substring(7,11));
        if(!"".equals(registerVO.getWx_openid())){
            user.setWxOpenid(registerVO.getWx_openid());
            user.setWxInfo(registerVO.getWx_info());
            user.setWx(registerVO.getWx());
            user.setNickname(registerVO.getWxNickName());
        }
        if(!"".equals(registerVO.getQq_openid())) {
            user.setQqOpenid(registerVO.getQq_openid());
            user.setQqInfo(registerVO.getQq_info());
            user.setQq(registerVO.getQq());
            user.setNickname(registerVO.getQqNickName());
        }
        if(!"".equals(registerVO.getSina_openid())) {
            user.setSinaOpenid(registerVO.getSina_openid());
            user.setSinaInfo(registerVO.getSina_info());
        }
        user.setLoginNum(1);
        user.setRegisterFrom(registerVO.getFrom());
        user.setRegisterTime(new Date());
        user.setIsAuth(0);
        user.setIsAllowInform(1);
        user.setIsAllowTalk(1);
        user.setState(1);
        user.setIsBuy(1);
        int result = this.create(user);
        if(result ==0){
            user.setUserId(UuidUtils.getShortUuid());
            this.create(user);
        }
        return user;
    }

    @Override
    public List<AvatarPO> listUserAvatar(List<String> listUserIds) {

        List<User> list =  userDao.listUserAvatar(listUserIds);
        List<AvatarPO> avatarPOs = new ArrayList<>();
        for(User user :list){
            AvatarPO avatarPO = new AvatarPO();
            avatarPO.setUserId(user.getUserId());
            avatarPO.setAvatar(user.getAvatar());
            avatarPOs.add(avatarPO);
        }
        return avatarPOs;
    }
}

package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import com.meirengu.uc.vo.UserVO;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    UserDao userDao;

    @Override
    public int create(User user) {
        return userDao.create(user);
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
}

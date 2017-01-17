package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

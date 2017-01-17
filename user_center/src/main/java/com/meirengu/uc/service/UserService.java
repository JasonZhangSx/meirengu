package com.meirengu.uc.service;

import com.meirengu.uc.model.User;

/**
 * 会员服务接口类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:19
 */
public interface UserService {

    /**
     * 新增会员
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 修改会员
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据会员Id获取会员信息
     * @param userId
     * @return
     */
    User retrieveByUserId(int userId);

    /**
     * 根据会员手机号获取会员信息
     * @param phone
     * @return
     */
    User retrieveByPhone(String phone);
}

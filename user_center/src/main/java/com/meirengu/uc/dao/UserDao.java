package com.meirengu.uc.dao;

import com.meirengu.uc.model.User;

/**
 * 会员数据访问对象类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:15
 */
public interface UserDao {

    /**
     * 新增会员
     *
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 修改会员信息
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据会员Id查询会员信息
     * @param userId
     * @return
     */
    User retrieveByUserId(int userId);

    /**
     * 根据会员手机号查询会员信息
     * @param phone
     * @return
     */
    User retrieveByPhone(String phone);

}

package com.meirengu.uc.dao;

import com.meirengu.dao.BaseDao;
import com.meirengu.uc.model.User;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 会员数据访问对象类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:15
 */
public interface UserDao extends BaseDao<User>{

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
     * 修改用户五种状态
     * @param user
     * @return
     */
    int updateState(User user);

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
    /**
     * 根据邀请人手机号查询会员信息
     * @param phone
     * @return
     */
    User selectByInviterPhone(String phone);

    /**
     * 根据手机号验证密码是否正确
     * @param map
     * @return
     */
    User verifyByPasswordAndPhone(Map map);

    int updatePasswordByPhone(User user);

    List<User> listUserAvatar(List<String> list);

    List<Map<String,Object>> getUserByPage(Map paramMap, RowBounds rowBounds);

    List<Map<String,Object>> getUserByPage(Map paramMap);

    int getUserCount(Map paramMap);

    int getBankIdCard(String bankIdcard);

    int getIdCard(String idcard);

    User retrieveByOpenId(String openId);

    int updateByPhone(User user);
}

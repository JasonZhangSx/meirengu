package com.meirengu.uc.service;

import com.meirengu.uc.model.User;
import com.meirengu.uc.po.AvatarPO;
import com.meirengu.uc.vo.RegisterVO;
import com.meirengu.uc.vo.UserVO;

import java.util.List;

/**
 * 会员服务接口类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:19
 */
public interface UserService {


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

    /**
     * 根据手机号和密码用户是否存在
     * @return
     */
//    User verifyByPasswordAndPhone(String mobile,String password);

    int updateUserInfo(UserVO userVO);

    int updatePasswordByPhone(User usr);

    int updateUserInfo(User user, String mobile, String ip, Integer from);

    User createUserInfo(String mobile, String password, Integer from, String ip, String mobileInviter,String avatar);

    User createUserInfo(RegisterVO registerVO);

    List<AvatarPO> listUserAvatar(List<String> listUserIds);
}

package com.meirengu.uc.service;

import com.meirengu.model.Page;
import com.meirengu.service.BaseService;
import com.meirengu.uc.model.User;
import com.meirengu.uc.vo.response.AvatarVO;
import com.meirengu.uc.vo.request.RegisterVO;
import com.meirengu.uc.vo.request.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 会员服务接口类
 *
 * @author Marvin
 * @create 2017-01-12 下午8:19
 */
public interface UserService extends BaseService<User>{


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

    /**
     * 更新用户信息
     * @param userVO
     * @return
     */
    int updateUserInfo(UserVO userVO);

    /**
     * 用户首次设置密码
     * @param usr
     * @return
     */
    int updatePasswordByPhone(User usr);

    /**
     * 更新用户信息
     * @param user
     * @param mobile
     * @param ip
     * @param from
     * @return
     */
    int updateUserInfo(User user, String mobile, String ip, Integer from);

    /**
     * 用户动态密码登陆创建用户
     * @param mobile
     * @param from
     * @param ip
     * @param avatar
     * @return
     */
    User createUserInfo(String mobile,Integer from, String ip,String avatar);

    /**
     * 用户注册方式 创建用户
     * @param registerVO
     * @return
     */
    User createUserInfo(RegisterVO registerVO);

    /**
     * 获取用户头像
     * @param listUserIds
     * @return
     */
    List<AvatarVO> listUserAvatar(List<String> listUserIds);

    /**
     * 调用支付系统获取余额信息
     * @param map
     */
    void getUserRestMoney(Map map);

    /**
     * 调取订单系统获取累计投资额
     * @param map
     */
    void getUserTotalInvestMoney(Map map);

    /*获取体现中金额*/
    void getWithdrawalsAmount(Map map);

    /*获取银行名称*/
    void getBankName(Map map);

    Page<User> getByPage(Page<User> page, Map paramMap);

    Page<User> getUserList(Page<User> page, Map paramMap);

    int modifyPayPassword(Integer userId,String mobile, String oldPassword, String newPassword);

    int setPayPassword(Integer userId, String newPassword);

    boolean getBankIdCard(String bankIdcard);

    boolean getIdCard(String idcard);

    User retrieveByOpenId(String openId);
}

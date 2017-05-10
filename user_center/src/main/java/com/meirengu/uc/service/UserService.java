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


    /*修改会员*/
    int update(User user);

    /* 根据会员Id获取会员信息*/
    User retrieveByUserId(int userId);

    /*根据会员手机号获取会员信息*/
    User retrieveByPhone(String phone);

    /* 更新用户信息*/
    int updateUserInfo(UserVO userVO);

    /** 用户首次设置密码*/
    int updatePasswordByPhone(User usr);

    /* 更新用户信息*/
    int updateUserInfo(User user, String mobile, String ip, Integer from);

    /* 用户动态密码登陆创建用户*/
    User createUserInfo(String mobile,Integer from, String ip,String avatar);

    /** 用户注册方式 创建用户*/
    User createUserInfo(RegisterVO registerVO);

    /* 获取用户头像*/
    List<AvatarVO> listUserAvatar(List<String> listUserIds);

    /*调用支付系统获取余额信息 */
    void getUserRestMoney(Map map);

    /**调取订单系统获取累计投资额 */
    void getUserTotalInvestMoney(Map map);

    /*获取体现中金额*/
    void getWithdrawalsAmount(Map map);

    /*获取银行名称*/
    void getBankName(Map map);

    /*分页获取用户信息*/
    Page<User> getByPage(Page<User> page, Map paramMap);

    /*不分页获取用户信息*/
    Page<User> getUserList(Page<User> page, Map paramMap);

    //修改支付密码
    int modifyPayPassword(Integer userId,String mobile, String oldPassword, String newPassword);

    //设置交易密码
    int setPayPassword(Integer userId, String newPassword);

    //获取银行卡号
    boolean getBankIdCard(String bankIdcard);

    //获取身份证
    boolean getIdCard(String idcard);

    //根据openId 查找用户
    User retrieveByOpenId(String openId);

    //绑定第三方
    int bundThirdParty(RegisterVO registerVO);

    //解除绑定第三方
    int unbund(String userId, Integer type);
}

package com.meirengu.uc.service;


import com.meirengu.uc.model.User;
import com.meirengu.uc.vo.response.RegisterInfo;

/**
 * 登录服务接口类
 *
 * @author Marvin
 * @create 2017-01-13 上午11:46
 */
public interface LoginService {


    RegisterInfo getNewToken(String token, Object object);

    RegisterInfo setUserToRedis(User usr);
}

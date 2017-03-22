package com.meirengu.uc.service;


import com.meirengu.uc.model.User;
import com.meirengu.uc.po.RegisterPO;

/**
 * 登录服务接口类
 *
 * @author Marvin
 * @create 2017-01-13 上午11:46
 */
public interface LoginService {


    RegisterPO getNewToken(String token,Object object);

    RegisterPO setUserToRedis(User usr);
}

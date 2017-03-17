package com.meirengu.uc.service.impl;

import com.meirengu.uc.model.User;
import com.meirengu.uc.po.RegisterPO;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.utils.RedisUtil;
import com.meirengu.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 登录服务实现类
 *
 * @author Marvin
 * @create 2017-01-13 上午11:51
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public RegisterPO getNewToken(String token,Object userRedis) {

        RedisUtil redisUtil = new RedisUtil();
        //删除本次token  建立一个新的有效token
        redisUtil.delkeyObject(token);
        String newToken = UUID.randomUUID().toString().replace("-", "");
        redisUtil.setObject(newToken,userRedis);
        RegisterPO registerPO = new RegisterPO();
        registerPO.setToken(newToken);
        registerPO.setUser((User) userRedis);
        return registerPO;
    }

    @Override
    public RegisterPO setUserToRedis(User usr) {

        RegisterPO registerPO = new RegisterPO();
        registerPO.setUser(usr);
        String token1 = UuidUtils.getUuid();
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setObject(token1,usr);
        registerPO.setToken(token1);
        return registerPO;
    }
}

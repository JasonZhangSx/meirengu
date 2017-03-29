package com.meirengu.uc.service.impl;

import com.meirengu.common.RedisClient;
import com.meirengu.common.TokenProccessor;
import com.meirengu.uc.model.User;
import com.meirengu.uc.po.RegisterPO;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录服务实现类
 *
 * @author Marvin
 * @create 2017-01-13 上午11:51
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisClient redisClient;
    @Override
    public RegisterPO getNewToken(String token,Object userRedis) {

        //删除本次token  建立一个新的有效token
        redisClient.delkeyObject(token);
        String newToken = TokenProccessor.getInstance().makeToken();
        Integer tokenTime = Integer.parseInt(ConfigUtil.getConfig("TOKEN_TIME"));
        redisClient.setObject(newToken,userRedis,tokenTime);
        RegisterPO registerPO = new RegisterPO();
        registerPO.setToken(newToken);
        registerPO.setUser((User) userRedis);
        registerPO.getUser().setPassword("");
        return registerPO;
    }

    @Override
    public RegisterPO setUserToRedis(User usr) {

        RegisterPO registerPO = new RegisterPO();
        registerPO.setUser(usr);
        String token = TokenProccessor.getInstance().makeToken();
        Integer tokenTime = Integer.parseInt(ConfigUtil.getConfig("TOKEN_TIME"));
        redisClient.setObject(token,usr,tokenTime);
        registerPO.setToken(token);
        registerPO.getUser().setPassword("");
        return registerPO;
    }
}

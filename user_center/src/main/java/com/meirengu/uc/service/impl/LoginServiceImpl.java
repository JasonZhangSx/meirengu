package com.meirengu.uc.service.impl;

import com.meirengu.common.RedisClient;
import com.meirengu.common.TokenProccessor;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.utils.TokenUtils;
import com.meirengu.uc.vo.request.TokenVO;
import com.meirengu.uc.vo.response.RegisterInfo;
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
    public RegisterInfo getNewToken(String token, Object userRedis) {

        //删除本次token  建立一个新的有效token
        redisClient.delkeyObject(token);
        String newToken = TokenProccessor.getInstance().makeToken();
        Integer tokenTime = Integer.parseInt(ConfigUtil.getConfig("TOKEN_TIME"));
        redisClient.setObject(newToken,userRedis,tokenTime);
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setToken(newToken);
        registerInfo.setUser((User) userRedis);
        registerInfo.getUser().setPassword("");
        return registerInfo;
    }

    @Override
    public RegisterInfo setUserToRedis(User usr,String deviceId) {

        String key = TokenUtils.getTokenKey(usr.getPhone());
        TokenVO tokenVO = (TokenVO) redisClient.getObject(key);
        if(tokenVO != null) {
            redisClient.delkeyObject(tokenVO.getToken());
        }
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setUser(usr);
        String token = TokenProccessor.getInstance().makeToken();
        Integer tokenTime = Integer.parseInt(ConfigUtil.getConfig("TOKEN_TIME"));

        tokenVO = new TokenVO();
        tokenVO.setToken(token);
        tokenVO.setDeviceId(deviceId);

//        this.setToken(key,tokenVO,token,usr,tokenTime);
        redisClient.setObject(token,usr,tokenTime);
        redisClient.setObject(key,tokenVO,tokenTime);


        registerInfo.setToken(token);
        registerInfo.getUser().setPassword("");
        return registerInfo;
    }

    private synchronized void  setToken(String key, TokenVO tokenVO, String token, User usr,Integer tokenTime){
        redisClient.setObject(key,tokenVO,tokenTime);
        redisClient.setObject(token,usr,tokenTime);
    }
}

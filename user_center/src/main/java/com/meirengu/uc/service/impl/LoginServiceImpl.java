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
import org.springframework.scheduling.annotation.Async;
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
        String newToken = TokenProccessor.getInstance().makeToken();
        Integer tokenTime = Integer.parseInt(ConfigUtil.getConfig("TOKEN_TIME"));

        this.setNewToken(newToken,token,userRedis,tokenTime);

        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setToken(newToken);
        registerInfo.setUser((User) userRedis);
        registerInfo.getUser().setPassword("");
        return registerInfo;
    }

    /**
     * god know what i coded
     * @param token
     * @param userRedis
     * @param tokenTime
     */
    @Async
    private synchronized void setNewToken(String newToken,String token, Object userRedis, Integer tokenTime) {

        User user = (User)userRedis;
        String key = TokenUtils.getTokenKey(user.getPhone());

        TokenVO tokenVO = (TokenVO) redisClient.getObject(key);
        tokenVO.setToken(newToken);
        redisClient.delkeyObject(token);
        redisClient.setObject(key,tokenVO,tokenTime);
        redisClient.setObject(newToken,user,tokenTime);
    }

    @Override
    public RegisterInfo setUserToRedis(User usr,String deviceId) {

        String key = TokenUtils.getTokenKey(usr.getPhone());
        String token = TokenProccessor.getInstance().makeToken();
        Integer tokenTime = Integer.parseInt(ConfigUtil.getConfig("TOKEN_TIME"));

        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setUser(usr);

        TokenVO tokenInfo = new TokenVO();
        tokenInfo.setToken(token);
        tokenInfo.setDeviceId(deviceId);
        //并发 消费了响应时间 但保证了token的绝对唯一性 一用户一token
        // 分布式下无效    呵呵··
        //你怎么优化呢？ redis分布式锁
        this.setToken(key,tokenInfo,token,usr,tokenTime);

        registerInfo.setToken(token);
        registerInfo.getUser().setPassword("");
        return registerInfo;
    }
    @Async
    private synchronized void  setToken(String key, TokenVO tokenInfo, String token, User usr,Integer tokenTime){

        TokenVO tokenVO = (TokenVO) redisClient.getObject(key);
        if(tokenVO != null) {
            redisClient.delkeyObject(tokenVO.getToken());
        }
        redisClient.setObject(key,tokenInfo,tokenTime);
        redisClient.setObject(token,usr,tokenTime);
    }
}

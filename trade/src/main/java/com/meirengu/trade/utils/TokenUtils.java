package com.meirengu.trade.utils;

import com.meirengu.common.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by root on 2017/3/28.
 */
@Component
public class TokenUtils {
    @Autowired
    private static RedisClient redisClient;

    public static boolean authToken(String token){
        return redisClient.existsBytes(token);
    }
}

package com.meirengu.utils;

import com.meirengu.common.RedisClient;

/**
 * token
 */

public class TokenUtils {

    private static RedisClient redisClient;

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public static boolean authToken(String token){
        return redisClient.existsBytes(token);
    }
}

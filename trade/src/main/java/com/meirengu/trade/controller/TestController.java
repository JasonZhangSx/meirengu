package com.meirengu.trade.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.meirengu.common.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-04-17 16:49
 */
@Controller
@RequestMapping("test")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("redis")
    public String redis(){
        try {
            String host = "r-tj7fb38117e64e24.redis.rds.aliyuncs.com";//控制台显示访问地址
            int port = 6379;
            Jedis jedis = new Jedis(host, port);
            //鉴权信息
            jedis.auth("Mrg2017Q2");//password
            String key = "redis";
            String value = "aliyun-redis";
            //select db默认为0
            jedis.select(1);
            //set一个key
            jedis.set(key, value);
            logger.info("Set Key " + key + " Value: " + value);
            //get 设置进去的key
            String getvalue = jedis.get(key);
            logger.info("Get Key " + key + " ReturnValue: " + getvalue);
            jedis.quit();
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/testRedis")
    public String testRedis(){
        redisClient.set("meirengu","123");
        logger.debug(redisClient.get("meirengu"));
        return redisClient.get("meirengu");
    }
}

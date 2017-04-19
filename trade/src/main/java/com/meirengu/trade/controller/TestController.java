package com.meirengu.trade.controller;

import com.meirengu.trade.rocketmq.Producer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
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

    @Autowired
    private Producer producer;



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

    @RequestMapping(value = "/testMq")
    public void  testMq(){
        String str = Long.toString(System.currentTimeMillis());
        Message msg = new Message("deploy", "testMq", str.getBytes());
        msg.setKeys("testMq" + str);
        //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h 22h 1d
        msg.setDelayTimeLevel(1);
        SendResult sendResult = null;
        try {
            logger.debug("发送消息：tag:testMq: {}", msg);
            sendResult = producer.getDefaultMQProducer().send(msg);
            logger.info("sendResult: {}", sendResult);
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

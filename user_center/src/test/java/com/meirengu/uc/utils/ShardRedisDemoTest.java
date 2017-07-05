package com.meirengu.uc.utils;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.model.Test01;
import com.meirengu.uc.model.Test02;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by huoyan403 on 6/2/2017.
 */
public class ShardRedisDemoTest {


    @Autowired
    RedisClient redisClient;
    public static void main(String[] args) throws InterruptedException {

//        ShardedJedis jedis = getShardedJedisPool().getResource();
//        int count = 10000;
//        long begin = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            sj.set("a" + i, "v" + i);
//        }
//        sj.close();
//        System.out.println(System.currentTimeMillis() - begin);
//        sj = src.getResource();
//        ShardedJedisPipeline p = sj.pipelined();
//        begin = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            p.set("ap" + i, "vp" + i);
//        }
//        p.sync();
//        sj.close();
//        System.out.println(System.currentTimeMillis() - begin);
//        BlockingQueue<String> logQueue = new LinkedBlockingQueue<String>();
//        begin = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            logQueue.put("i=" + i);
//        }
//        System.out.println(System.currentTimeMillis() - begin);



        JedisPoolConfig config = new JedisPoolConfig();
        RedisClient redisService = new RedisClient(config, "192.168.0.135:6379");

//        ShardedJedis sj = redisService.getShardedJedisPool().getResource();
//        int count = 10000;
        long begin = System.currentTimeMillis();
        Test01 test01 = new Test01();
        test01.setTest("test");
        redisService.setObject("test01",test01);
        Test02 test02 = (Test02) redisService.getObject("test01");
        System.err.print(test02);
//        for (int i = 0; i < count; i++) {
//            sj.set("a" + i, "v" + i);
//        }
//        sj.close();
        System.out.println(System.currentTimeMillis() - begin);
    }

}

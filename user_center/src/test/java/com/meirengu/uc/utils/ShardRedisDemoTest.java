package com.meirengu.uc.utils;

import com.meirengu.common.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
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
    }

}

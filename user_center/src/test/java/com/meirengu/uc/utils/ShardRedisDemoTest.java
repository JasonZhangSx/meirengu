//package com.meirengu.uc.utils;
//
//import com.meirengu.common.RedisClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPipeline;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
///**
// * Created by huoyan403 on 6/2/2017.
// */
//public class ShardRedisDemoTest {
//
//
//    @Autowired
//    RedisClient redisClient;
//    public static void main(String[] args) throws InterruptedException {
////        RedisClient src = new RedisClient();
////        src.setServers("redis://172.23.26.135:7379");
////        src.init();
////        ShardedJedis sj = src.getResource();
//
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
//    }
//
//}

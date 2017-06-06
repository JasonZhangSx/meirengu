package com.meirengu.uc.thread;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.dao.AreasDao;
import com.meirengu.uc.model.Area;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import java.util.Date;
import java.util.List;

/**
 * Created by huoyan403 on 3/22/2017.
 */
public class AddressToRedisThread implements Runnable{


    private static final Logger logger = LoggerFactory.getLogger(AddressToRedisThread.class);
    private AreasDao areasDao;
    private RedisClient redisClient;

    public void setAreasDao(AreasDao areasDao) {
        this.areasDao = areasDao;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     * 异步保存国家地址表到Redis
     * key---area_+ areaId
     */
    @Override
    public void run() {
        List<Area> list = areasDao.getAreaData();
        logger.info("set area to redis start time :{} ",new Date());
        this.setAreaList(list);
        logger.info("set area to redis end time :{} ",new Date());
    }

    public void setAreaList(List<Area> list) {

        ShardedJedis jedis = redisClient.getShardedJedisPool().getResource();
        ShardedJedisPipeline pipeline = jedis.pipelined();
        for (Area area:list){
            String value = JacksonUtil.toJSon(area);
            pipeline.set("area_"+area.getAreaId(),value);
        }
        pipeline.sync();
        jedis.close();
    }
}

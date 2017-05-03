package com.meirengu.uc.thread;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huoyan403 on 3/22/2017.
 */
public class AddressToRedisThread implements Runnable{


    private static final Logger logger = LoggerFactory.getLogger(AddressToRedisThread.class);
    private AreasMapper areasMapper;
    private RedisClient redisClient;
    public AreasMapper getAreasMapper() {
        return areasMapper;
    }

    public void setAreasMapper(AreasMapper areasMapper) {
        this.areasMapper = areasMapper;
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public void run() {
        /**
         * 异步保存国家地址表到Redis
         * key---area_+ areaId
         */
        List<Area> list = new ArrayList<Area>();
        list = areasMapper.getAreaData();
        logger.info("set area form redis start :{} ",list);
        for (Area area:list){
            redisClient.delkeyObject("area_"+area.getAreaId());
            String value = JacksonUtil.toJSon(area);
            redisClient.setObject("area_"+area.getAreaId(),value,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        logger.info("set area form redis end  :{}",list);
    }
}

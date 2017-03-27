package com.meirengu.uc.thread;

import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.utils.RedisUtil;
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

    public AreasMapper getAreasMapper() {
        return areasMapper;
    }

    public void setAreasMapper(AreasMapper areasMapper) {
        this.areasMapper = areasMapper;
    }

    @Override
    public void run() {
        /**
         * 异步保存国家地址表到Redis
         * key---area_+ areaId
         */
        List<Area> list = new ArrayList<Area>();
        list = areasMapper.getAreaData();
        RedisUtil redisUtil = new RedisUtil();
        for (Area area:list){
            redisUtil.delkeyObject("area_"+area.getAreaId());
            String value = JacksonUtil.toJSon(area);
            redisUtil.setObject("area_"+area.getAreaId(),value,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        logger.info("set area form redis :{}","");
    }
}

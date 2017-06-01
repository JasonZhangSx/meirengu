package com.meirengu.uc.init;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 容器启动后加载redis 因时间过长 约八分钟 暂不使用该方法加载
 * Created by huoyan403 on 3/22/2017.
 */
@Component
public class AddressToRedisInit{


    private static final Logger logger = LoggerFactory.getLogger(AddressToRedisInit.class);
    @Autowired
    private AreasMapper areasMapper;
    @Autowired
    private RedisClient redisClient;

//    启动开关
//    @PostConstruct
    public void AddressToRedisInit() {
        /**
         * 异步保存国家地址表到Redis
         * key---area_+ areaId
         */
        List<Area> list = new ArrayList<Area>();
        list = areasMapper.getAreaData();
        logger.info("set area form redis start time :{} ",new Date());
        logger.info("set area form redis start :{} ",list);
        for (Area area:list){
            redisClient.delkeyObject("area_"+area.getAreaId());
            String value = JacksonUtil.toJSon(area);
            redisClient.setObject("area_"+area.getAreaId(),value,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        logger.info("set area form redis end  :{}",list);
        logger.info("set area form redis end time :{} ",new Date());
    }
}

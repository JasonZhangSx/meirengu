package com.meirengu.uc.init;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.dao.AreasDao;
import com.meirengu.uc.thread.AddressToRedisThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 容器启动后加载redis
 * Created by huoyan403 on 3/22/2017.
 */
@Component
public class AddressToRedisInit{

    private static final Logger logger = LoggerFactory.getLogger(AddressToRedisInit.class);
    @Autowired
    private AreasDao areasDao;
    @Autowired
    private RedisClient redisClient;

//    启动开关
    @PostConstruct
    public void AddressToRedisInit() {

        AddressToRedisThread addressToRedisThread = new AddressToRedisThread();
        addressToRedisThread.setRedisClient(redisClient);
        addressToRedisThread.setAreasDao(areasDao);
        addressToRedisThread.run();

    }
}

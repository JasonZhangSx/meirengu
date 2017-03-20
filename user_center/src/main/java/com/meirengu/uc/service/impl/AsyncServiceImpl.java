package com.meirengu.uc.service.impl;

import com.meirengu.uc.model.User;
import com.meirengu.uc.service.AsyncService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/20/2017.
 */
@Service
public class AsyncServiceImpl implements AsyncService{

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);


    @Async
    @Override
    public void initPayAccount(User user) {

        Thread t = Thread.currentThread();
        String name = t.getName();
        logger.info("AsyncServiceImpl run Thread.name start:{}",name);

        for(int i = 0; i < 3 ; i++ ){
            HttpResult hr = null;
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", user.getPhone());
            params.put("userId", user.getUserId()+"");
            params.put("content", JacksonUtil.toJSon(user));
            String url = ConfigUtil.getConfig("URI_INIT_USER_PAYACCOUNT");
            logger.info("UserServiceImpl.initUserPayAccount post >> uri :{}, params:{}", new Object[]{url, params});
            try {
                hr = HttpUtil.doPostForm(url,params);
            } catch (Exception e) {
                logger.error("CheckCodeServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
            }
            if(hr.getStatusCode() == 200){
                return;
            }
            logger.info("AsyncServiceImpl run Thread.name end:{}",name);
        }

    }
}

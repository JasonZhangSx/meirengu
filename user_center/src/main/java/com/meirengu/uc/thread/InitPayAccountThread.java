package com.meirengu.uc.thread;

import com.meirengu.common.StatusCode;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.EnableRetry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/20/2017.
 */
@EnableRetry
public class InitPayAccountThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(InitPayAccountThread.class);

    private Integer userId;
    private String mobile;

    public InitPayAccountThread(Integer userId, String mobile) {
        this.userId = userId;
        this.mobile = mobile;
    }

    @Override
    public void run(){

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("mobile", mobile);

            Map<String, String> params = new HashMap<String, String>();
            params.put("content", JacksonUtil.toJSon(map));
            String url = ConfigUtil.getConfig("URI_INIT_USER_PAYACCOUNT");
            logger.info("InitPayAccountThread.initUserPayAccount post first>> uri :{}, params:{}", url, params);

            HttpResult hr = null;
            hr = HttpUtil.doPostForm(url, params);
            if(hr.getStatusCode() != StatusCode.OK){
                Thread.sleep(5000L);
                logger.info("InitPayAccountThread.initUserPayAccount post second>> uri :{}, params:{}", url, params);
                hr = HttpUtil.doPostForm(url, params);
            }
        } catch (Exception e) {
            logger.info("InitPayAccountThread initPayAccount throws Exception :{}",e.getMessage());
        }
    }
}

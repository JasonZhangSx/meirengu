package com.meirengu.uc.thread;

import com.meirengu.common.StatusCode;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/30/2017.
 */
public class ReceiveCouponsThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ReceiveCouponsThread.class);

    private Integer userId;
    private String userPhone;

    public ReceiveCouponsThread(Integer userId, String userPhone) {
        this.userId = userId;
        this.userPhone = userPhone;
    }

    @Override
    public void run() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId+"");
            map.put("user_phone", userPhone);
            map.put("mark", "1");//此处固定写死为1
            map.put("activity_identification", "");
            String url = ConfigUtil.getConfig("URI_RECEIVE_COUPONS");
            logger.info("InitPayAccountThread.initUserPayAccount post first>> uri :{}, params:{}", new Object[]{url, map});

            HttpResult hr = null;
            hr = HttpUtil.doPostForm(url, map);
            if(hr.getStatusCode() != StatusCode.OK){
                Thread.sleep(5000L);
                logger.info("InitPayAccountThread.initUserPayAccount post second>> uri :{}, params:{}", new Object[]{url, map});
                hr = HttpUtil.doPostForm(url, map);
            }
        } catch (Exception e) {
            logger.info("ReceiveCouponsThread receiveCoupons throws Exception :{}",e.getMessage());
        }
    }
}

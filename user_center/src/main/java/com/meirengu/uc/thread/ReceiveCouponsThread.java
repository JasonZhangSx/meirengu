package com.meirengu.uc.thread;

import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

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
            receiveCoupons();
        } catch (Exception e) {
            logger.info("ReceiveCouponsThread receiveCoupons throws Exception :{}",e.getMessage());
        }
    }
    @Retryable(value= {RemoteAccessException.class},maxAttempts = 3,backoff = @Backoff(delay = 5000l,multiplier = 1))
    private void receiveCoupons() throws Exception{
        HttpResult hr = null;

        Map<String, String> map = new HashMap<>();
        map.put("user_id", userId+"");
        map.put("user_phone", userPhone);
        map.put("mark", "1");//此处固定写死为1
        map.put("activity_identification", "");

        String url = ConfigUtil.getConfig("URI_RECEIVE_COUPONS");

        logger.info("InitPayAccountThread.initUserPayAccount post >> uri :{}, params:{}", new Object[]{url, map});

        hr = HttpUtil.doPostForm(url, map);
        if(hr.getStatusCode()!=200){
            new RemoteAccessException("RPC调用异常");
        }

    }

    @Recover
    public void recover(RemoteAccessException e) {
        System.out.println("重试回调执行");
        System.out.println(e.getMessage());
    }
}

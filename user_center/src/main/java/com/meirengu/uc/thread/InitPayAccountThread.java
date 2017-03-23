package com.meirengu.uc.thread;

import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

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
    private Integer times;
    /**
     *@Retryable注解
     *被注解的方法发生异常时会重试
     *value:指定发生的异常进行重试
     *include:和value一样，默认空，当exclude也为空时，所有异常都重试
     *exclude:指定异常不重试，默认空，当include也为空时，所有异常都重试
     *maxAttemps:重试次数，默认3
     *backoff:重试补偿机制，默认没有
     */
    @Override
    public void run(){

        try {
            comeon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   @Retryable(value= {RemoteAccessException.class},maxAttempts = 3,backoff = @Backoff(delay = 5000l,multiplier = 1))
   public void comeon() throws Exception{
       HttpResult hr = null;

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("mobile", mobile);

        Map<String, String> params = new HashMap<String, String>();
        params.put("content", JacksonUtil.toJSon(map));
        String url = ConfigUtil.getConfig("URI_INIT_USER_PAYACCOUNT");

        logger.info("InitPayAccountThread.initUserPayAccount post >> uri :{}, params:{}", new Object[]{url, params});

        hr = HttpUtil.doPostForm(url, params);
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

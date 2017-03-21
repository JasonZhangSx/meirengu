package com.meirengu.uc.thread;

import com.meirengu.uc.model.User;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/20/2017.
 */
public class InitPayAccountThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(InitPayAccountThread.class);

    private User user;

    public InitPayAccountThread(User user) {
        this.user = user;
    }

    @Override
    public void run() {

        for(int i = 1; i < 4 ; i++ ){
            HttpResult hr = null;
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", user.getPhone());
            params.put("userId", user.getUserId()+"");
            params.put("content", JacksonUtil.toJSon(user));
            String url = ConfigUtil.getConfig("URI_INIT_USER_PAYACCOUNT");
            logger.info("InitPayAccountThread.initUserPayAccount post >> uri :{}, params:{}", new Object[]{url, params});
            try {
                hr = HttpUtil.doPostForm(url,params);
            } catch (Exception e) {
                logger.error("InitPayAccountThread.send error >> params:{}, exception:{}", new Object[]{params, e});
            }
            if(hr.getStatusCode() == 200){
                logger.info("InitPayAccountThread run Thread. the "+i+" times success:{}",hr);
                return;
            }else{
                logger.info("InitPayAccountThread run Thread. the "+i+" times failed:{}",hr);
            }
        }
    }
}

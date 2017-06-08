package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.webview.service.ActivityService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动接口实现类
 *
 * @author Marvin
 * @create 2017-03-30 下午5:52
 */
@Service
public class ActivityServiceImpl implements ActivityService{

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Override
    public JSONObject activities() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", "1");
        params.put("per_page", "20");
        params.put("sortby", "create_time");
        params.put("order", "desc");
        params.put("status", "2");
        String url = ConfigUtil.getConfig("URI_ACTIVITIES");
        logger.info("activities http >> url:{}, params:{}", new Object[]{url, params});
        HttpResult hr = null;
        try {
            hr = HttpUtil.doPostForm(url, params);
        } catch (Exception e) {
            logger.error("activities http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("activities result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return data;
            }else {
                logger.info("activities result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
            }
        }else{
            logger.error("activities http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }
}

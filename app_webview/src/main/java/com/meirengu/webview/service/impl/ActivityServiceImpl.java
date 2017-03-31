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
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", "1");
        paramsMap.put("per_page", "20");
        paramsMap.put("sortby", "create_time");
        paramsMap.put("order", "desc");
        String url = ConfigUtil.getConfig("URI_ACTIVITIES");
        logger.info("activities http >> url:{}, params:{}", new Object[]{url, paramsMap});
        HttpResult hr = null;
        try {
            hr = HttpUtil.doPost(url, paramsMap);
        } catch (Exception e) {
            logger.error("activities http exception << url:{},  params:{}, exception:{}", new Object[]{url, paramsMap, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("activities result success << url:{}, params:{}, data:{}", new Object[]{url, paramsMap, data});
                return data;
            }else {
                logger.info("activities result failure << url:{}, params:{}, code:{}", new Object[]{url, paramsMap, code});
            }
        }else{
            logger.error("activities http failure << url:{},  params:{}, http_code:{}", new Object[]{url, paramsMap, hr.getStatusCode()});
        }
        return null;
    }
}

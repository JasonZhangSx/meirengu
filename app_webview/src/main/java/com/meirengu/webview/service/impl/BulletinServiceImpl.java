package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.BulletinService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台公告接口实现类
 *
 * @author Marvin
 * @create 2017-03-30 下午7:20
 */
@Service
public class BulletinServiceImpl implements BulletinService {

    private static final Logger logger = LoggerFactory.getLogger(BulletinServiceImpl.class);

    @Override
    public JSONObject bulletins() {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", "1");
        paramsMap.put("per_page", "20");
        paramsMap.put("sortby", "create_time");
        paramsMap.put("order", "desc");
        paramsMap.put("status", "1");
        String url = ConfigUtil.getConfig("URI_BULLETINS");
        logger.info("bulletins http >> url:{}, params:{}", new Object[]{url, paramsMap});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doGet(url, paramsMap);
        } catch (Exception e) {
            logger.error("bulletins http exception << url:{},  params:{}, exception:{}", new Object[]{url, paramsMap, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("bulletins result success << url:{}, params:{}, data:{}", new Object[]{url, paramsMap, data});
                return data;
            }else {
                logger.info("bulletins result failure << url:{}, params:{}, code:{}", new Object[]{url, paramsMap, code});
            }
        }else{
            logger.error("bulletins http failure << url:{},  params:{}, http_code:{}", new Object[]{url, paramsMap, hr.getStatusCode()});
        }
        return null;
    }
}

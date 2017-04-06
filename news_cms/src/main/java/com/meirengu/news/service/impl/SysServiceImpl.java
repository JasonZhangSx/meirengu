package com.meirengu.news.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.news.service.SysService;
import com.meirengu.news.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统服务实现
 * @author 建新
 * @create 2017-04-06 16:51
 */
@Service
public class SysServiceImpl implements SysService{

    @Override
    public Map<String, Object> initApp(Integer appId, Integer appChannel, Integer status) {

        Map<String, Object> returnMap = new HashMap<>();
        try {
            Map<String, Object> map = getLastVersion(appId, status, appChannel);
            returnMap.put("version", map);
            Object sysTime = getSysTime();
            returnMap.put("sysTime", sysTime);
            return returnMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Object getSysTime() throws IOException {
        HttpUtil.HttpResult hr = HttpUtil.doGet(ConfigUtil.getValue("sys.time.url"));
        if(hr.getStatusCode() == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return jsonObject.get("data");
            }
        }
        return null;
    }

    private Map getLastVersion(Integer appId, Integer status, Integer appChannel){
        Map<String, String> params = new HashMap<>();
        params.put("app_id", String.valueOf(appId));
        params.put("app_channel", String.valueOf(appChannel));
        params.put("status", String.valueOf(status));

        HttpUtil.HttpResult hr = HttpUtil.doPostForm(ConfigUtil.getValue("sys.version.url"), params);
        if(hr.getStatusCode() == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return (Map) jsonObject.get("data");
            }
        }
        return null;
    }
}

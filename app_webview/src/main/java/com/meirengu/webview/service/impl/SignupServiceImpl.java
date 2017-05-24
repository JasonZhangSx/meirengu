package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.SignupService;
import com.meirengu.webview.utils.ConfigUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 报名活动服务层
 * @author 建新
 * @create 2017-05-24 16:44
 */
@Service
public class SignupServiceImpl implements SignupService{
    @Override
    public JSONObject signup(String name, String telphone, Integer type, String city) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_SIGNUP"));
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("telphone", telphone);
        params.put("type", String.valueOf(type));
        params.put("city", city);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.meirengu.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-28 16:46
 */
public class BaseController {

    public Object httpGet(String url) throws IOException {
        HttpUtil.HttpResult hr = HttpUtil.doGet(url);
        Object data = getData(hr);
        return data;
    }

    public Object httpPost(String url, Map<String, String> params){
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, params);
        Object data = getData(hr);
        return data;
    }

    private Object getData(HttpUtil.HttpResult hr){
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(200)){
                Object data = jsonObject.get("data");
                return data;
            }
        }
        return null;
    }
}

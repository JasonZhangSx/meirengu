package com.meirengu.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * 封装返回数据对象
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Result setResult(int code, Object data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (code == 200 && (data != null && !"".equals(data))){
            result.setData(data);
        }else {
            //result.setData("");
        }
        LOGGER.info("Request getResponse: {}", JSON.toJSON(result));
        return result;
    }

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

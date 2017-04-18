package com.meirengu.cf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.cf.service.OtherService;
import com.meirengu.cf.utils.ConfigUtil;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 非众筹项目服务
 * @author 建新
 * @create 2017-04-17 10:49
 */
@Service
public class OtherServiceImpl implements OtherService {

    @Override
    public Map<String, Object> getAreasByLastLevel(Integer areaId) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("area.parents.get"));
        url.append("?area_id=").append(areaId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    JSONObject itemJson = (JSONObject) jsonObject.get("data");
                    return JSON.parseObject(itemJson.toJSONString(), Map.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

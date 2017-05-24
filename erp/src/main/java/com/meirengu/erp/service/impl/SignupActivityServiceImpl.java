package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.service.SignupActivityService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 报名活动服务层
 *
 * @author 建新
 * @create 2017-05-24 15:54
 */
@Service
public class SignupActivityServiceImpl implements SignupActivityService{
    @Override
    public Map<String, Object> query(int page, int perPage, boolean isPage, Integer type, String name, String
            telphone, String city) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("activity.signup.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        if(type != null && type != 0){
            url.append("&type=").append(type);
        }
        if(!StringUtil.isEmpty(name)){
            url.append("&name=").append(name);
        }
        if(!StringUtil.isEmpty(telphone)){
            url.append("&telphone=").append(telphone);
        }
        if(!StringUtil.isEmpty(city)){
            url.append("&city=").append(city);
        }

        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    return (Map) jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

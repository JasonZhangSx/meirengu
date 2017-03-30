package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.Item;
import com.meirengu.erp.service.ItemService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目服务实现
 * @author 建新
 * @create 2017-03-29 19:32
 */
@Service
public class ItemServiceImpl implements ItemService{

    @Override
    public Map<String, Object> getItemListByPage(boolean isPage, Integer itemId, String itemName, String itemStatus) {

        Map<String, Object> map = new HashMap<>();

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.list"));

        url.append("?is_page=").append(isPage);
        if(!StringUtil.isEmpty(itemId)){
            url.append("&item_id=").append(itemId);
        }
        if(!StringUtil.isEmpty(itemName)){
            url.append("&item_name=").append(itemName);
        }
        if(!StringUtil.isEmpty(itemStatus)){
            url.append("&item_status=").append(itemStatus);
        }

        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public boolean itemAdd(Map<String, String> params) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.insert"));

        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(200)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List getItemClassList(Map<String, String> params) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.class.list"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(200)){
                List list = (List) jsonObject.get("data");
                return list;
            }
        }
        return null;
    }
}

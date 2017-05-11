package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.ItemService;
import com.meirengu.webview.utils.ConfigUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 项目service
 * @author 建新
 * @create 2017-05-09 19:14
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public Map<String, Object> getItemDetail(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_ITEM"));
        url.append("/").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    Map map = (Map) jsonObject.get("data");
                    return map;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getLevelList(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_LEVEL"));
        url.append("?item_id=").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    List list = (List) jsonObject.get("data");
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getContentList(Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_CONTENT"));
        url.append("?item_id=").append(itemId);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    List list = (List) jsonObject.get("data");
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.Article;
import com.meirengu.erp.model.IpWhiteList;
import com.meirengu.erp.service.IpWhiteService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * IP白名单服务实现层
 * @author 建新
 * @create 2017-07-24 11:55
 */
@Service
public class IpWhiteServiceImpl implements IpWhiteService{

    @Override
    public Map<String, Object> query(int page, int perPage, boolean isPage, Integer status) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("ip.white.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        if(status != null){
            url.append("&status=").append(status);
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

    @Override
    public Map<String, Object> detail(int id) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("ip.white.detail"));
        url.append("/").append(id);
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

    @Override
    public Map<String, Object> add(IpWhiteList ipWhiteList) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("ip.white.add"));
        params.put("ip", ipWhiteList.getIp() == null ? "" : ipWhiteList.getIp());
        params.put("description", ipWhiteList.getDescription() == null ? "" : ipWhiteList.getDescription());
        params.put("type", String.valueOf(ipWhiteList.getType()));
        params.put("url", ipWhiteList.getUrl());
        params.put("status", String.valueOf(ipWhiteList.getStatus()));

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

    @Override
    public Map<String, Object> update(IpWhiteList ipWhiteList) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("ip.white.update"));
        params.put("id", String.valueOf(ipWhiteList.getId()));
        params.put("ip", ipWhiteList.getIp() == null ? "" : ipWhiteList.getIp());
        params.put("description", ipWhiteList.getDescription() == null ? "" : ipWhiteList.getDescription());
        params.put("type", String.valueOf(ipWhiteList.getType()));
        params.put("url", ipWhiteList.getUrl());
        params.put("status", String.valueOf(ipWhiteList.getStatus()));

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

    @Override
    public Map<String, Object> setCache() {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("ip.white.setCache"));

        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
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

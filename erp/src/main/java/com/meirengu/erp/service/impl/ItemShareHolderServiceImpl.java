package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.ItemShareholder;
import com.meirengu.erp.service.ItemShareHolderService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目股东服务层
 * @author 建新
 * @create 2017-03-29 21:34
 */
@Service
public class ItemShareHolderServiceImpl implements ItemShareHolderService{
    @Override
    public Object query(int page, int perPage, boolean isPage, Integer itemId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.shareholder.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        if(itemId != null && itemId != 0){
            url.append("&item_id=").append(itemId);
        }
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(200)){
                    return jsonObject.get("data");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> detail(int id) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.shareholder.detail"));
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
    public Map<String, Object> add(ItemShareholder shareholder) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.shareholder.insert"));

        params.put("item_id", String.valueOf(shareholder.getItemId()));
        params.put("shareholder_name", shareholder.getShareholderName());
        params.put("shareholder_idcard", shareholder.getShareholderIdcard());
        params.put("shareholder_address", shareholder.getShareholderAddress());
        params.put("shareholder_telphone", shareholder.getShareholderTelphone());
        params.put("shareholder_amount", String.valueOf(shareholder.getShareholderAmount()));
        params.put("before_invest_rate", String.valueOf(shareholder.getBeforeInvestRate()));
        params.put("after_invest_rate", String.valueOf(shareholder.getAfterInvestRate()));

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
    public Map<String, Object> update(ItemShareholder shareholder) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.shareholder.update"));

        params.put("id", String.valueOf(shareholder.getId()));
        params.put("item_id", String.valueOf(shareholder.getItemId()));
        params.put("shareholder_name", shareholder.getShareholderName());
        params.put("shareholder_idcard", shareholder.getShareholderIdcard());
        params.put("shareholder_address", shareholder.getShareholderAddress());
        params.put("shareholder_telphone", shareholder.getShareholderTelphone());
        params.put("shareholder_amount", String.valueOf(shareholder.getShareholderAmount()));
        params.put("before_invest_rate", String.valueOf(shareholder.getBeforeInvestRate()));
        params.put("after_invest_rate", String.valueOf(shareholder.getAfterInvestRate()));

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
    public Map<String, Object> delete(int id) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("item.shareholder.delete"));
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
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

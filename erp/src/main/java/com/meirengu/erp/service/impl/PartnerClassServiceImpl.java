package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.LeadInvestor;
import com.meirengu.erp.model.PartnerClass;
import com.meirengu.erp.service.InvestorService;
import com.meirengu.erp.service.PartnerClassService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 行业分类service实现层
 * @author 建新
 * @create 2017-05-19 11:36
 */
@Service
public class PartnerClassServiceImpl implements PartnerClassService{
    @Override
    public Object query(int page, int perPage, boolean isPage) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        url.append("&flag=1");
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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.detail"));
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
    public Map<String, Object> add(PartnerClass partnerClass) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.insert"));
        params.put("class_name", partnerClass.getClassName());
        params.put("class_description", partnerClass.getClassDescription());

        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return (Map) jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> update(PartnerClass partnerClass) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.update"));
        params.put("class_id", String.valueOf(partnerClass.getClassId()));
        params.put("class_name", partnerClass.getClassName());
        params.put("class_description", partnerClass.getClassDescription());
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return (Map) jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.delete"));
        Map<String, String> params = new HashMap<String, String>();
        params.put("class_id", String.valueOf(id));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                return (Map) jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

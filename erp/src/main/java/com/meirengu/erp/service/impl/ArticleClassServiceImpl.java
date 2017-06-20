package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.ArticleClass;
import com.meirengu.erp.model.LeadInvestor;
import com.meirengu.erp.service.ArticleClassService;
import com.meirengu.erp.service.InvestorService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-05-19 11:36
 */
@Service
public class ArticleClassServiceImpl implements ArticleClassService{
    @Override
    public Object query(int page, int perPage, boolean isPage, Integer flag) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.class.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        if(flag != null){
            url.append("&flag=").append(flag);
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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.class.detail"));
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
    public Map<String, Object> add(ArticleClass articleClass) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.class.add"));
        params.put("ac_code", articleClass.getAcCode());
        params.put("ac_name", articleClass.getAcName());
        params.put("ac_parent_id", articleClass.getAcParentId() == null ? "0" : String.valueOf(articleClass.getAcParentId()));
        params.put("ac_sort", articleClass.getAcSort() == null ? "255" : String.valueOf(articleClass.getAcSort()));
        params.put("flag", articleClass.getFlag() == null ? "1" : String.valueOf(articleClass.getFlag()));

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
    public Map<String, Object> update(ArticleClass articleClass) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.class.update"));
        params.put("ac_id", String.valueOf(articleClass.getAcId()));
        params.put("ac_code", articleClass.getAcCode());
        params.put("ac_name", articleClass.getAcName());
        params.put("ac_parent_id", articleClass.getAcParentId() == null ? "0" : String.valueOf(articleClass.getAcParentId()));
        params.put("ac_sort", articleClass.getAcSort() == null ? "255" : String.valueOf(articleClass.getAcSort()));
        params.put("flag", articleClass.getFlag() == null ? "1" : String.valueOf(articleClass.getFlag()));
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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.class.delete"));
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
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

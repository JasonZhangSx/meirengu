package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.Article;
import com.meirengu.erp.service.ArticleService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章serviceImpl
 * @author 建新
 * @create 2017-05-19 11:36
 */
@Service
public class ArticleServiceImpl implements ArticleService{
    @Override
    public Map<String, Object> query(int page, int perPage, boolean isPage) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);

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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.detail"));
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
    public Map<String, Object> add(Article article) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.add"));
        params.put("ac_id", String.valueOf(article.getAcId()));
        params.put("article_url", article.getArticleUrl() == null ? "" : article.getArticleUrl());
        params.put("article_label", article.getArticleLabel() == null ? "" : article.getArticleLabel());
        params.put("article_show", String.valueOf(article.getArticleShow()));
        params.put("article_sort", String.valueOf(article.getArticleSort()));
        params.put("article_img", article.getArticleImg() == null ? "" : article.getArticleImg());
        params.put("article_title", article.getArticleTitle());
        params.put("article_content", article.getArticleContent() == null ? "" : article.getArticleContent());
        params.put("article_is_banner", String.valueOf(article.getArticleIsBanner()));
        params.put("article_is_commend", String.valueOf(article.getArticleIsCommend()));
        params.put("article_is_publish", String.valueOf(article.getArticleIsPublish()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(article.getArticleTime() == null){
            article.setArticleTime(new Date());
        }
        params.put("article_time", format.format(article.getArticleTime()));
        params.put("flag", String.valueOf(article.getFlag() == null ? "1" : article.getFlag()));
        params.put("create_user", String.valueOf(article.getCreateUser() == null ? "0" : article.getCreateUser()));
        params.put("create_user_name", article.getCreateUserName() == null ? "" : article.getCreateUserName());
        params.put("create_user_img", article.getCreateUserImg() == null ? "" : article.getCreateUserImg());
        params.put("create_user_type", String.valueOf(article.getCreateUserType() == null ? "0" : article.getCreateUserType()));

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
    public Map<String, Object> update(Article article) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.update"));
        params.put("article_id", String.valueOf(article.getArticleId()));
        params.put("ac_id", String.valueOf(article.getAcId()));
        params.put("article_url", article.getArticleUrl() == null ? "" : article.getArticleUrl());
        params.put("article_label", article.getArticleLabel() == null ? "" : article.getArticleLabel());
        params.put("article_show", String.valueOf(article.getArticleShow()));
        params.put("article_sort", String.valueOf(article.getArticleSort()));
        params.put("article_img", article.getArticleImg() == null ? "" : article.getArticleImg());
        params.put("article_title", article.getArticleTitle());
        params.put("article_content", article.getArticleContent() == null ? "" : article.getArticleContent());
        params.put("article_is_banner", String.valueOf(article.getArticleIsBanner()));
        params.put("article_is_commend", String.valueOf(article.getArticleIsCommend()));
        params.put("article_is_publish", String.valueOf(article.getArticleIsPublish()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(article.getArticleTime() == null){
            article.setArticleTime(new Date());
        }
        params.put("article_time", format.format(article.getArticleTime()));
        params.put("flag", String.valueOf(article.getFlag() == null ? "1" : article.getFlag()));
        params.put("create_user", String.valueOf(article.getCreateUser() == null ? "0" : article.getCreateUser()));
        params.put("create_user_name", article.getCreateUserName() == null ? "" : article.getCreateUserName());
        params.put("create_user_img", article.getCreateUserImg() == null ? "" : article.getCreateUserImg());
        params.put("create_user_type", String.valueOf(article.getCreateUserType() == null ? "0" : article.getCreateUserType()));
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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("article.delete"));
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

package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.VersionUpgrade;
import com.meirengu.erp.service.VersionService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-05-17 17:44
 */
@Service
public class VersionServiceImpl implements VersionService{
    @Override
    public Map<String, Object> query(int page, int perPage, boolean isPage, int appChannel, int appId, String
            versionCode, int status) {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("version.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        if(appChannel != 0){
            url.append("&app_channel=").append(appChannel);
        }
        if(appId != 0){
            url.append("&appId=").append(appId);
        }
        if(!StringUtil.isEmpty(versionCode)){
            url.append("&version_code=").append(versionCode);
        }
        if(status != 0){
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

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("version.detail"));
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
    public Map<String, Object> add(VersionUpgrade versionUpgrade) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("version.add"));
        params.put("app_id", String.valueOf(versionUpgrade.getAppId()));
        params.put("app_code", versionUpgrade.getAppCode());
        params.put("app_name", versionUpgrade.getAppName());
        params.put("app_channel", String.valueOf(versionUpgrade.getAppChannel()));
        params.put("version_milepost", String.valueOf(versionUpgrade.getVersionMilepost()));
        params.put("version_code", versionUpgrade.getVersionCode());
        params.put("version_code_before", versionUpgrade.getVersionCodeBefore());
        params.put("version_size", versionUpgrade.getVersionSize());
        params.put("download_url", versionUpgrade.getDownloadUrl());
        params.put("update_title", versionUpgrade.getUpdateTitle());
        params.put("update_content", versionUpgrade.getUpdateContent());
        params.put("status", String.valueOf(versionUpgrade.getStatus()));
        params.put("update_type", String.valueOf(versionUpgrade.getUpdateType()));

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
    public Map<String, Object> update(VersionUpgrade versionUpgrade) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("version.update"));
        params.put("id", String.valueOf(versionUpgrade.getId()));
        params.put("app_id", String.valueOf(versionUpgrade.getAppId()));
        params.put("app_code", versionUpgrade.getAppCode());
        params.put("app_name", versionUpgrade.getAppName());
        params.put("app_channel", String.valueOf(versionUpgrade.getAppChannel()));
        params.put("version_milepost", String.valueOf(versionUpgrade.getVersionMilepost()));
        params.put("version_code", versionUpgrade.getVersionCode());
        params.put("version_code_before", versionUpgrade.getVersionCodeBefore());
        params.put("version_size", versionUpgrade.getVersionSize());
        params.put("download_url", versionUpgrade.getDownloadUrl());
        params.put("update_title", versionUpgrade.getUpdateTitle());
        params.put("update_content", versionUpgrade.getUpdateContent());
        params.put("status", String.valueOf(versionUpgrade.getStatus()));
        params.put("update_type", String.valueOf(versionUpgrade.getUpdateType()));

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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("version.delete"));
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

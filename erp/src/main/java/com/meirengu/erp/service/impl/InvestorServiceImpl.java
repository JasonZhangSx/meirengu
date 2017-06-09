package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.LeadInvestor;
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
public class InvestorServiceImpl implements InvestorService{
    @Override
    public Object query(int page, int perPage, boolean isPage) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("investor.list"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);

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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("investor.detail"));
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
    public Map<String, Object> add(LeadInvestor investor) {

        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("investor.add"));
        params.put("investor_name", investor.getInvestorName());
        params.put("investor_type", String.valueOf(investor.getInvestorType()));
        params.put("principal_name", investor.getPrincipalName());
        params.put("investor_business_licence", investor.getInvestorBusinessLicence());
        params.put("investor_idcard", investor.getInvestorIdcard());
        params.put("investor_address", investor.getInvestorAddress());
        params.put("investor_telphone", investor.getInvestorTelphone());
        params.put("investor_image", investor.getInvestorImage());
        params.put("investor_introduction", investor.getInvestorIntroduction());
        params.put("investor_company", investor.getInvestorCompany());
        params.put("investor_position", investor.getInvestorPosition());
        params.put("investor_idea", investor.getInvestorIdea());
        params.put("operate_account", "admin");

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
    public Map<String, Object> update(LeadInvestor investor) {
        Map<String, String> params = new HashMap<>();
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("investor.update"));
        params.put("id", String.valueOf(investor.getId()));
        params.put("investor_name", investor.getInvestorName());
        params.put("investor_type", String.valueOf(investor.getInvestorType()));
        params.put("principal_name", investor.getPrincipalName());
        params.put("investor_business_licence", investor.getInvestorBusinessLicence());
        params.put("investor_idcard", investor.getInvestorIdcard());
        params.put("investor_address", investor.getInvestorAddress());
        params.put("investor_telphone", investor.getInvestorTelphone());
        params.put("investor_image", investor.getInvestorImage());
        params.put("investor_introduction", investor.getInvestorIntroduction());
        params.put("investor_company", investor.getInvestorCompany());
        params.put("investor_position", investor.getInvestorPosition());
        params.put("investor_idea", investor.getInvestorIdea());
        params.put("operate_account", investor.getOperateAccount());
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
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("investor.delete"));
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

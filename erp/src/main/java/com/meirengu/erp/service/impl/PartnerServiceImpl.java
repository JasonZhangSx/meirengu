package com.meirengu.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.model.Partner;
import com.meirengu.erp.model.PartnerClass;
import com.meirengu.erp.service.PartnerService;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 合作方服务层
 * @author 建新
 * @create 2017-03-29 21:34
 */
@Service
public class PartnerServiceImpl implements PartnerService{

    @Override
    public List getPartnerList() {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.list"));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    List<Partner> list = (List) jsonObject.get("data");
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getPartnerClassList() {

        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.list"));
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(url.toString());
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null && code.equals(StatusCode.OK)){
                    List<PartnerClass> list = (List) jsonObject.get("data");
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean partnerAdd(Map params) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("partner.class.list"));
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url.toString(), params);
        int statusCode = hr.getStatusCode();
        if(statusCode == StatusCode.OK){
            String content = hr.getContent();
            JSONObject jsonObject = JSONObject.parseObject(content);
            Object code = jsonObject.get("code");
            if(code != null && code.equals(StatusCode.OK)){
                return true;
            }
        }
        return false;
    }
}

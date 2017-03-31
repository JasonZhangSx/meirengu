package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.FaqService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * FAQ接口实现类
 *
 * @author Marvin
 * @create 2017-03-30 下午9:31
 */
@Service
public class FaqServiceImpl implements FaqService {

    private static final Logger logger = LoggerFactory.getLogger(FaqServiceImpl.class);

    @Override
    public JSON faqClasses() {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_FAQ_CLASSES"));
        String params = "?status=1";
        url.append(params);
        logger.info("faqClasses http >> url:{}, params:{}", new Object[]{url, params});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doGet(url.toString());
        } catch (Exception e) {
            logger.error("faqClasses http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONArray data = (JSONArray) jsonObject.get("data");
                logger.info("faqClasses result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return data;
            }else {
                logger.info("faqClasses result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
            }
        }else{
            logger.error("faqClasses http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }

    @Override
    public JSON faqByClassId(int classId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_CLASS_FAQS"));
        String params = "?page=1&per_page=20&status=1&class_id="+classId;
        url.append(params);
        logger.info("faqByClassId http >> url:{}, params:{}", new Object[]{url, params});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doGet(url.toString());
        } catch (Exception e) {
            logger.error("faqByClassId http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("faqByClassId result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return data;
            }else {
                logger.info("faqByClassId result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
            }
        }else{
            logger.error("faqByClassId http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }
}

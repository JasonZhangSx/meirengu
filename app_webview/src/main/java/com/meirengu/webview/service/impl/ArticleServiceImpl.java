package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.ArticleService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 文章接口实现层
 * @author 建新
 * @create 2017-06-07 11:17
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Override
    public Object query(int page, int perPage, boolean isPage, int acId) {
        StringBuffer url = new StringBuffer(ConfigUtil.getConfig("URI_ARTICLE"));
        url.append("?is_page=").append(isPage).append("&page=").append(page).append("&per_page=").append(perPage);
        if(acId != 0){
            url.append("&ac_id=").append(acId);
        }
        logger.info("article http >> url:{}", new Object[]{url});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doGet(url.toString());
        } catch (Exception e) {
            logger.error("article http exception << url:{}, exception:{}", new Object[]{url, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("article result success << url:{}, data:{}", new Object[]{url,  data});
                return data;
            }else {
                logger.info("article result failure << url:{}, code:{}", new Object[]{url, code});
            }
        }else{
            logger.error("article http failure << url:{},  http_code:{}", new Object[]{url, hr.getStatusCode()});
        }
        return null;
    }
}

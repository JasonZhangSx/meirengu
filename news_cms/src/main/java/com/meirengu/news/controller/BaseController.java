package com.meirengu.news.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.news.common.Constants;
import com.meirengu.news.common.StatusCode;
import com.meirengu.news.model.Article;
import com.meirengu.news.model.Page;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/29.
 */
public class BaseController {

    /**
     * 封装分页参数
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Page setPageParams(int pageSize, int pageNum){
        Page page = new Page();
        if(StringUtils.isEmpty(pageNum)){
            pageNum = Constants.PAGE_NUM_DEFAULT;
        }
        if(StringUtils.isEmpty(pageSize)){
            pageSize = Constants.PAGE_SIZE_DEFAULT;
        }
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

    /**
     * 封装返回数据
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Map<String, Object> setReturnMsg(String code, Object data, String msg){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if("200".equals(code)){
            returnMap.put("code", code);
            returnMap.put("msg", msg);
            if(!StringUtils.isEmpty(data)){
                returnMap.put("data",JSONObject.toJSON(data));
            }
        }else{
            returnMap.put("code", code);
            returnMap.put("msg", msg);
        }
        return returnMap;
    }
}

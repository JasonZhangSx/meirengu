package com.meirengu.news.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.model.Result;
import com.meirengu.news.common.Constants;
import com.meirengu.news.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/29.
 */
@CrossOrigin
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    /**
     * 封装分页参数
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Page setPageParams(int pageNum, int pageSize){
        Page page = new Page();
        if(pageNum == 0){
            pageNum = Constants.PAGE_NUM_DEFAULT;
        }
        if(pageSize == 0){
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
        if(com.meirengu.news.common.StatusCode.STATUS_200.equals(code)){
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
    /**
     * 封装返回数据对象
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Result setResult(int code, Object data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (code == 200 && (data != null && !"".equals(data))){
            result.setData(data);
        }
        LOGGER.info("Request getResponse: {}", JSON.toJSON(result));
        return result;
    }

}

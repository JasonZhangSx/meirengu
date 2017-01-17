package com.meirengu.mall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.common.Constants;
import com.meirengu.mall.common.StatusCode;
import com.meirengu.mall.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

/**
 * controller基类
 *
 * @author 建新
 * @create 2017-01-10 19:35
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
        if(StatusCode.STATUS_200.equals(code)){
            returnMap.put("code", code);
            returnMap.put("msg", msg);
            if(!StringUtils.isEmpty(data)){
                returnMap.put("data",JSONObject.toJSON(data));
            }
        }else{
            returnMap.put("code", code);
            returnMap.put("msg", msg);
        }
        LOGGER.info("Request getResponse: {}", JSON.toJSON(returnMap));
        return returnMap;
    }

}

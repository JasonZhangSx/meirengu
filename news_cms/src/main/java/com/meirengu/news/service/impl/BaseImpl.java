package com.meirengu.news.service.impl;

import com.google.gson.Gson;
import com.meirengu.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/25 16:18.
 */
public abstract class BaseImpl {
    private final static Logger logger = LoggerFactory.getLogger(BaseImpl.class);

    /**
     * @param code code码
     * @param map 数据
     * @return 返回对应数据
     */
    protected static String getResult(Integer code, Map<String, Object> map){
        logger.info("Request getResult parameter:{},{}", code,map);
        Map<String,Object> resultMap = new HashMap<>();
        Gson gson = new Gson();
        String reslut="";
        try {
            resultMap.put("code",code);
            resultMap.put("msg", StatusCode.codeMsgMap.get(code));
            resultMap.put("data",map);
            reslut=gson.toJson(resultMap);
            logger.info("Return getResult result:{}", reslut.toString());
            return reslut;
        }catch (Exception e){
            resultMap.put("code", StatusCode.MB_ERROR);
            resultMap.put("msg", StatusCode.codeMsgMap.get(StatusCode.MB_ERROR));
            reslut=gson.toJson(resultMap);
            logger.info("Return getResult result:{}", reslut.toString());
            return reslut;
        }
    }
}

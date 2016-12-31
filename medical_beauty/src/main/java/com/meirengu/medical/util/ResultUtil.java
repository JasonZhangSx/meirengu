package com.meirengu.medical.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/30 20:36.
 * 返回结果公共类
 */
public class ResultUtil {
    private final static Logger logger = LoggerFactory.getLogger(ResultUtil.class);

    /**
     * @param code code码
     * @param map 数据
     * @return 返回对应数据
     */
    public static String getResult(String code, Map<String, Object> map){
        logger.info("Request getResult parameter:{},{}", code,map);
        Map<String,Object> resultMap = new HashMap<>();
        Gson gson = new Gson();
        String reslut="";
        try {
            resultMap.put("code",code);
            resultMap.put("msg",CodeUtil.getMsg(code));
            resultMap.put("data",map);
            reslut=gson.toJson(resultMap);
            logger.info("Return getResult result:{}", reslut.toString());
            return reslut;
        }catch (Exception e){
            resultMap.put("code", CodeUtil.ERROR.getCode());
            resultMap.put("msg", CodeUtil.ERROR.getMsg());
            reslut=gson.toJson(resultMap);
            logger.info("Return getResult result:{}", reslut.toString());
            return reslut;
        }
    }
}

package com.meirengu.medical.util;

import com.google.gson.Gson;
import com.meirengu.common.StatusCode;
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
//            resultMap.put("msg",CodeUtil.getMsg(code));
            resultMap.put("msg",StatusCode.codeMsgMap.get(Integer.valueOf(code)));
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

    /**
     * 判断其它接口状态并取值
     * @param json
     * @return
     */
    public static Object judgeStatus(String json){
        Gson gson = new Gson();
        Map caseMap =gson.fromJson(json,Map.class);
        if (caseMap.get("code").equals("200")){
            caseMap= (Map) caseMap.get("data");
            return caseMap.get("list");
        }
        return null;
    }
}

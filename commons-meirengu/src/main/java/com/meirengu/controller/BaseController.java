package com.meirengu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制器类
 *
 * @author Marvin
 * @create 2017-01-11 下午6:06
 */
@CrossOrigin
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
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

    /**
     * 封装返回数据
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Map<String, Object> setReturnMsg(int code, Object data, String msg){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(code == 200 && (data != null && !"".equals(data))){
            returnMap.put("data",JSONObject.toJSON(data));
        }
        returnMap.put("code", code);
        returnMap.put("msg", msg);
        LOGGER.info("Request getResponse: {}", JSON.toJSON(returnMap));
        return returnMap;
    }

}

package com.meirengu.uc.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信接口类
 *
 * @author Marvin
 * @create 2017-01-11 下午6:06
 */
public class BaseController {

    /**
     * 封装返回数据
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Map<String, Object> setReturnMsg(int code, Object data, String msg){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(code == 200){
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

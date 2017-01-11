package com.meirengu.mall.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.mall.common.Constants;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/29.
 */
public class BaseController {

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

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
     * 封装分页参数
     * @param pageSize
     * @param pageNum
     * @return
     */
    /*public Page setPageParams(int pageSize, int pageNum){
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
    }*/

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


    /**
     * 将返回数据封装为json字符串
     * @param code
     * @param data
     * @param msg
     * @return
     */
    public String toJsonReturnMsg(String code, Object data, String msg){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if(!StringUtils.isEmpty(data)){
            json.put("data", data);
        }
        return json.toJSONString();
    }


    public static void main(String args){
        String s = new BaseController().toJsonReturnMsg("200", 111, "ok");
        System.out.print(s);
    }
}

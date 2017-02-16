package com.meirengu.uc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Test
 *
 * @author Marvin
 * @create 2017-02-16 下午8:11
 */
public class Test {
    public static void main(String[] args) {
        String Content = "{\"code\":200,\"data\":{\"code\":0,\"msg\":\"发送成功\",\"count\":1,\"fee\":0.05," +
                "\"unit\":\"RMB\",\"mobile\":\"13811930842\",\"sid\":\"13758719520\"},\"msg\":\"成功\"}";
        JSONObject result = JSON.parseObject(Content);
        System.out.println("1111111>>>" + result.get("code"));
        System.out.println("2222222>>>" + result.get("data"));
        System.out.println("3333333>>>" + result.get("code").toString());
        if ("200".equals(result.getString("code"))){
            System.out.println("4444444>>>" + result.get("data"));
        }
    }
}

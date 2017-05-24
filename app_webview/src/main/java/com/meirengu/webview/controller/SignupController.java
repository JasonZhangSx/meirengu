package com.meirengu.webview.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import com.meirengu.webview.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 报名活动controller
 * @author 建新
 * @create 2017-05-24 16:52
 */
@Controller
@RequestMapping("activity/signup")
public class SignupController extends BaseController{

    @Autowired
    SignupService signupService;

    @RequestMapping
    public String signup(){
        return "activity_signup";
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submit(String telphone, String name, Integer type, String city) throws UnsupportedEncodingException {

        name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        city = new String(city.getBytes("ISO-8859-1"), "UTF-8");
        JSONObject result = null;
        if(StringUtil.isEmpty(telphone) || StringUtil.isEmpty(name) || StringUtil.isEmpty(type) || StringUtil.isEmpty(city)){
            result = new JSONObject();
            result.put("code", StatusCode.MISSING_ARGUMENT);
            result.put("msg", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }else {
            if(!ValidatorUtil.isMobile(telphone)){
                result = new JSONObject();
                result.put("code", StatusCode.MOBILE_FORMAT_ERROR);
                result.put("msg", StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }else {
                result = signupService.signup(name, telphone, type, city);
            }
        }

        return result;
    }
}

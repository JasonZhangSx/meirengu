package com.meirengu.webview.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.utils.StringUtil;
import com.meirengu.utils.ValidatorUtil;
import com.meirengu.webview.service.SignupService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    SignupService signupService;

    @RequestMapping
    public String signup(){
        return "helen_activity/activity_signup";
    }

    @RequestMapping("ml0shop")
    public String ml0shop(){
        return "helen_activity/ml_0_shop";
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submit(String telphone, String name, Integer type, String city) throws UnsupportedEncodingException {

        logger.info("before name: {}   city: {}",name,city);
//        name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
//        city = new String(city.getBytes("ISO-8859-1"), "UTF-8");
//        logger.info("after name: {}   city: {}",name,city);

        JSONObject result = null;
        if(StringUtil.isEmpty(telphone) || StringUtil.isEmpty(type)){
            result = new JSONObject();
            result.put("code", StatusCode.MISSING_ARGUMENT);
            result.put("msg", StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }else {
            if(!ValidatorUtil.isMobile(telphone)){
                result = new JSONObject();
                result.put("code", StatusCode.MOBILE_FORMAT_ERROR);
                result.put("msg", StatusCode.codeMsgMap.get(StatusCode.MOBILE_FORMAT_ERROR));
            }else if((StringUtils.isNotBlank(name) && name.length()>10) || (StringUtils.isNotBlank(city) && city.length()>10)){
                result = new JSONObject();
                result.put("code", StatusCode.INVALID_ARGUMENT);
                result.put("msg", StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
            }else {
                result = signupService.signup(name, telphone, type, city);
            }
        }

        return result;
    }
}

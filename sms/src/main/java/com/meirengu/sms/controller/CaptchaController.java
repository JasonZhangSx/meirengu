package com.meirengu.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.sms.model.Captcha;
import com.meirengu.sms.service.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * 验证码控制器
 *
 * @author Marvin
 * @create 2017-01-13 上午9:33
 */
@RestController
public class CaptchaController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    CaptchaService captchaService;


    @RequestMapping(value = "captcha/validate", method = RequestMethod.POST)
    public Result validate(@RequestParam(required = true) String mobile,
                           @RequestParam(required = true) int code,
                           @RequestParam(required = true) String uid){
        logger.info("CaptchaController.validate params >> mobile:{},code:{},uid:{}", new Object[]{mobile,code,uid});
        Captcha captcha = captchaService.retrieve(mobile,code,uid);
        if (captcha == null){
            return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
        }
        Date expireTime = captcha.getExpireTime();
        Date nowTime = new Date();
        if (expireTime.compareTo(nowTime) < 0){
            return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode.CAPTCHA_EXPIRE));
        }
        //update status
        captcha.setUse(true);
        captcha.setUsingTime(nowTime);
        int result = captchaService.update(captcha);
        if (result > 0){
            return super.setResult(StatusCode.OK, captcha, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.DB_OPERATION_FAIL, captcha, StatusCode.codeMsgMap.get(StatusCode.DB_OPERATION_FAIL));
        }

    }

    @RequestMapping(value = "captcha", method = RequestMethod.POST)
    public Result create(@RequestParam(required = true) Captcha captcha){
        logger.info("CaptchaController.create params >> {}", JSONObject.toJSON(captcha));
        if (captcha == null){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int result = captchaService.create(captcha);
        if (result > 0){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.DB_OPERATION_FAIL, null, StatusCode.codeMsgMap.get(StatusCode.DB_OPERATION_FAIL));
        }

    }

    @RequestMapping(value = "captcha", method = RequestMethod.PUT)
    public Result update(@RequestParam(required = true) Captcha captcha){
        logger.info("CaptchaController.update params >> {}", JSONObject.toJSON(captcha));
        if (captcha == null){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        int result = captchaService.update(captcha);
        if (result > 0){
            return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.DB_OPERATION_FAIL, null, StatusCode.codeMsgMap.get(StatusCode.DB_OPERATION_FAIL));
        }

    }

    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public Result retrieve(@RequestParam(required = true) String mobile, int code, String uid){
        logger.info("CaptchaController.retrieve params >> mobile:{}, code:{}, uid:{}", new Object[]{mobile, code, uid});
        if (StringUtils.isBlank(mobile)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Captcha captcha = captchaService.retrieve(mobile, code, uid);
        if (captcha != null){
            return super.setResult(StatusCode.OK, captcha, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            return super.setResult(StatusCode.DB_OPERATION_FAIL, null, StatusCode.codeMsgMap.get(StatusCode.DB_OPERATION_FAIL));
        }

    }



}
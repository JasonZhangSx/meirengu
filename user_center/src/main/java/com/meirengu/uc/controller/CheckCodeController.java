package com.meirengu.uc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 验证码控制器
 *
 * @author Marvin
 * @create 2017-02-07 下午11:44
 */
@RestController
public class CheckCodeController extends BaseController {

    @Autowired
    CheckCodeService checkCodeService;

    private static final Logger logger = LoggerFactory.getLogger(CheckCodeController.class);

    /**
     * 获取验证码
     * @param mobile
     * @param ip
     * @return
     */
    @RequestMapping(value = "check_code", method = RequestMethod.POST)
    public Result create(@RequestParam(required = true) String mobile, String ip) {
        logger.info("CheckCodeController.create params >> mobile:{}", mobile);
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        int code = checkCodeService.generate();
        HttpResult hr = checkCodeService.send(mobile, code, ip);
        if (hr != null) {
            logger.info("checkCodeService.send <===StatusCode:{}, Content:{}, Response:{}", hr.getStatusCode(), hr
                    .getContent(), hr.getResponse());
            if (hr.getStatusCode() == StatusCode.OK) {
                JSONObject resultObj = JSON.parseObject(hr.getContent());
                if ("200".equals(resultObj.getString("code"))) {
                    //store db
                    CheckCode checkCode = new CheckCode();
                    checkCode.setMobile(mobile);
                    checkCode.setCode(code);
                    Date nowTime = new Date();
                    Date expireTime = new Date(nowTime.getTime() + Long.valueOf(ConfigUtil.getConfig
                            ("EXPIRE_CHECKCODE_LOGIN")));
                    checkCode.setCreateTime(nowTime);
                    checkCode.setExpireTime(expireTime);
                    checkCode.setIp(StringUtils.defaultString(ip, ""));
                    checkCode.setUse(false);
                    int createResult = checkCodeService.create(checkCode);
                    logger.info("checkCodeService.create result <==={}", createResult);
                    return super.setResult(StatusCode.OK, code, StatusCode.codeMsgMap.get(StatusCode.OK));
                }
            }

        }
        return super.setResult(StatusCode.CHECK_CODE_SEND_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                .CHECK_CODE_SEND_ERROR));
    }





    @RequestMapping(value = "vitifyCode", method = RequestMethod.POST)
    public Result vitifyCode(@RequestParam(value = "mobile", required = true) String mobile,
                         @RequestParam(value = "check_code", required = true) String checkCode) {
        logger.info("CheckCodeController.vitifyCode params >> mobile:{}", mobile ,checkCode);
        //verify params
        if (StringUtils.isEmpty(mobile) || !ValidatorUtil.isMobile(mobile)) {
            return setResult(StatusCode.MOBILE_FORMAT_ERROR, null, StatusCode.codeMsgMap.get(StatusCode
                    .MOBILE_FORMAT_ERROR));
        }
        CheckCode code = checkCodeService.retrieve(mobile, Integer.valueOf(checkCode));
        if (code == null) {
            return super.setResult(StatusCode.CAPTCHA_INVALID, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_INVALID));
        }
        if (code.getExpireTime().compareTo(new Date()) < 0) {
            return super.setResult(StatusCode.CAPTCHA_EXPIRE, null, StatusCode.codeMsgMap.get(StatusCode
                    .CAPTCHA_EXPIRE));
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }




}

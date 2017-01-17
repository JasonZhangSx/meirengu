package com.meirengu.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.uc.service.LoginService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.utils.HttpUtil;
import com.meirengu.uc.utils.HttpUtil.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务实现类
 *
 * @author Marvin
 * @create 2017-01-13 上午11:51
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public HttpResult captchaLoginValidate(String apikey, String mobile, int code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", apikey);
        params.put("mobile",mobile);
        params.put("code",code);
        HttpResult hr = null;
        try {
            hr = HttpUtil.doPost(ConfigUtil.getConfig("URI_CAPTCHA_VALIDATE"), JSONObject.toJSONString(params));
        } catch (Exception e){
            logger.error("LoginServiceImpl.captchaLoginValidate error : {}", e);
        }
        return hr;
    }
}

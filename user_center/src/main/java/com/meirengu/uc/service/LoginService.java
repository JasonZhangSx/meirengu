package com.meirengu.uc.service;

import com.meirengu.uc.utils.HttpUtil.HttpResult;

/**
 * 登录服务接口类
 *
 * @author Marvin
 * @create 2017-01-13 上午11:46
 */
public interface LoginService {

    public HttpResult captchaLoginValidate(String apikey, String mobile, int code);
}

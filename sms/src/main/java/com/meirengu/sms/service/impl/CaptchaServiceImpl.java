package com.meirengu.sms.service.impl;

import com.meirengu.sms.dao.CaptchaDao;
import com.meirengu.sms.model.Captcha;
import com.meirengu.sms.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午7:24
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    CaptchaDao captchaDao;


    @Override
    public int create(Captcha captcha) {
        return captchaDao.create(captcha);
    }

    @Override
    public int update(Captcha captcha) {
        return captchaDao.update(captcha);
    }

    @Override
    public Captcha retrieve(String mobile, int code, String uid) {
        Map params = new HashMap();
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("uid",uid);
        return captchaDao.retrieve(params);
    }
}

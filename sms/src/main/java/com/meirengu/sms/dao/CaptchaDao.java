package com.meirengu.sms.dao;

import com.meirengu.sms.model.Captcha;

import java.util.Map;

/**
 * 验证码数据访问对象类
 *
 * @author Marvin
 * @create 2017-01-12 下午7:30
 */
public interface CaptchaDao {

    /**
     * 新增验证码
     *
     * @param captcha
     * @return
     */
    int create(Captcha captcha);

    /**
     * 修改验证码
     * @param captcha
     * @return
     */
    int update(Captcha captcha);

    /**
     * 获取验证码
     * @param params
     * @return
     */
    Captcha retrieve(Map params);

}

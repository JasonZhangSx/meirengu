package com.meirengu.sms.service;

import com.meirengu.sms.model.Captcha;
import org.springframework.stereotype.Service;

/**
 * 验证码服务接口类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:06
 */
@Service
public interface CaptchaService {

    /**
     * 新增验证码
     *
     * @param captcha
     * @return
     */
    int create(Captcha captcha);

    /**
     * 修改验证码
     *
     * @param captcha
     * @return
     */
    int update(Captcha captcha);

    /**
     * 获取验证码
     *
     * @param mobile
     * @param code
     * @param uid
     * @return
     */
    Captcha retrieve(String mobile, int code, String uid);

}

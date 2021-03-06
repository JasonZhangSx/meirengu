package com.meirengu.uc.service;

import com.meirengu.uc.model.CheckCode;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.springframework.stereotype.Service;

/**
 * 验证码服务接口类
 *
 * @author Marvin
 * @create 2017-01-12 下午3:06
 */
@Service
public interface CheckCodeService {

    /**
     * 生成验证码
     */
    int generate();

    /**
     * 发送验证码
     */
    HttpResult send(String mobile, int code, String ip,String type);

    /**
     * 保存验证码
     */
    int create(CheckCode checkCode);

    /**
     * 修改验证码
     */
    int update(CheckCode checkCode);

    /**
     * 获取验证码
     */
    CheckCode retrieve(String mobile, int code);

}

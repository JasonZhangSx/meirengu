package com.meirengu.uc.service;

import com.meirengu.uc.model.CheckCode;
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
     * @return
     */
    int generate();

    /**
     * 新增验证码
     *
     * @param checkCode
     * @return
     */
    int create(CheckCode checkCode);

    /**
     * 修改验证码
     *
     * @param checkCode
     * @return
     */
    int update(CheckCode checkCode);

    /**
     * 获取验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    CheckCode retrieve(String mobile, int code);

}

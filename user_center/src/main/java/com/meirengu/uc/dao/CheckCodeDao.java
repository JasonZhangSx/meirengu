package com.meirengu.uc.dao;

import com.meirengu.uc.model.CheckCode;

import java.util.Map;

/**
 * 验证码数据访问对象类
 *
 * @author Marvin
 * @create 2017-01-12 下午7:30
 */
public interface CheckCodeDao {

    /**
     * 新增验证码
     *
     * @param checkCode
     * @return
     */
    int create(CheckCode checkCode);

    /**
     * 修改验证码
     * @param checkCode
     * @return
     */
    int update(CheckCode checkCode);

    /**
     * 获取验证码
     * @param params
     * @return
     */
    CheckCode retrieve(Map params);

}

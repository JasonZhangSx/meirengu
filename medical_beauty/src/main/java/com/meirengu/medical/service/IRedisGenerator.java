package com.meirengu.medical.service;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/10 12:01.
 */
public interface IRedisGenerator {
    /**
     * 向redis中添加对象
     * @param keyValue key
     * @param object value
     * @return 成功or失败
     */
    boolean add(String keyValue,Object object);
}

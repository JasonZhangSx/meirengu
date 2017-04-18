package com.meirengu.cf.service;

import java.util.Map;

/**
 * 非众筹项目的其他服务
 *
 * @author 建新
 * @create 2017-04-17 10:46
 */
public interface OtherService {

    Map<String, Object> getAreasByLastLevel(Integer areaId);
}

package com.meirengu.mall.dao;

import com.meirengu.mall.model.ServiceCode;

import java.util.Map;

/**
 * 服务码dao
 * @author 建新
 * @create 2017-02-23 17:53
 */
public interface ServiceCodeDao {

    /**
     * 查询
     * @param map
     * @return
     */
    int query(Map map);

    /**
     * 新增
     * @param code
     * @return
     */
    int insert(ServiceCode code);

    /**
     * 更新
     * @param code
     * @return
     */
    int validateCode(ServiceCode code);

    /**
     * 通过code获取serviceCode详情
     * @param code
     * @return
     */
    ServiceCode getDetailByCode(int code);
}

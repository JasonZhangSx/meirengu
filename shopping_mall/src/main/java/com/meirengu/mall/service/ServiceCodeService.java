package com.meirengu.mall.service;

import com.meirengu.mall.model.ServiceCode;

import java.util.Map;

/**
 * 服务码接口
 * @author 建新
 * @create 2017-02-23 18:04
 */
public interface ServiceCodeService {

    /**
     * 生成服务码
     * @return
     */
    String create(int hospitalId, String orderSN, String userPhone, String itemDesc,
                  double originalPrice, double orderPrice, double restPrice, int itemId);

    /**
     * 查询服务码列表
     * @return
     */
    ServiceCode query(Map map);

    /**
     * 验证服务码
     * @param code
     * @return
     */
    int validateCode(int code, int hospitalId);

    /**
     * 通过code获取serviceCode详情
     * @param code
     * @return
     */
    ServiceCode getDetailByCode(int code);
}

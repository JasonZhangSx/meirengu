package com.meirengu.mall.service;

import com.meirengu.mall.model.ServiceCode;
import com.meirengu.utils.StringUtil;

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
    Map<String, Object> generate(int hospitalId, String orderSN, String userPhone, int itemId);

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
    int validateCode(String code, int hospitalId);

    /**
     * 通过code获取serviceCode详情
     * @param code
     * @return
     */
    ServiceCode getDetailByCode(String code);

    /**
     * 通过orderSN获取serviceCode详情
     * @param orderSN
     * @return
     */
    ServiceCode getDetailByOrderSN(String orderSN);
}

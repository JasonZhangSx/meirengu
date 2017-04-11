package com.meirengu.cf.service;
import com.meirengu.cf.model.Partner;
import com.meirengu.service.BaseService;

import java.util.Map;

/**
 * Partner服务接口 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface PartnerService extends BaseService<Partner>{

    /**
     * 获取协议内容
     * @param itemId
     * @return
     */
    Map<String, Object> getAgreementContent(int itemId, int levelId);
}

package com.meirengu.cf.service;
import com.meirengu.cf.model.ItemExtention;
import com.meirengu.service.BaseService;

import java.util.Map;

/**
 * ItemExtention服务接口 
 * @author 建新
 * @create Tue May 23 14:41:52 CST 2017
 */
public interface ItemExtentionService extends BaseService<ItemExtention>{
    /**
     * 通过投资人id获取项目id
     * @param leadInvestorId
     * @return
     */
    String getIdsByInvestorId(int leadInvestorId);
}

package com.meirengu.trade.service;
import com.meirengu.model.Page;
import com.meirengu.trade.model.Rebate;
import com.meirengu.service.BaseService;

import java.util.Map;

/**
 * Rebate服务接口 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateService extends BaseService<Rebate>{

    /**
     * 根据优惠券号更新
     * @param rebate
     * @return
     */
    int updateBySn(Rebate rebate);
    /**
     * 根据条件进行分页
     * @param page
     * @param map
     * @return
     */
    Page getRebateFullInfoByPage(Page page, Map map);
}

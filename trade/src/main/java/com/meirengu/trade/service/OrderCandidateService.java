package com.meirengu.trade.service;
import com.meirengu.trade.model.OrderCandidate;
import com.meirengu.service.BaseService;

import java.io.IOException;

/**
 * OrderCandidate服务接口 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface OrderCandidateService extends BaseService<OrderCandidate>{

    /**
     * 新增候补预约
     * @param orderCandidate
     * @return
     */
    int insertCandidate(OrderCandidate orderCandidate) throws IOException;
}

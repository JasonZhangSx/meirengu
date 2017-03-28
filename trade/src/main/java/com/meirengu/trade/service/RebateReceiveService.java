package com.meirengu.trade.service;
import com.meirengu.model.Result;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.service.BaseService;

import java.math.BigDecimal;
import java.util.List;

/**
 * RebateReceive服务接口 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateReceiveService extends BaseService<RebateReceive>{

    /**
     * 领取优惠券
     * @param userId
     * @param userPhone
     * @param batchIdList
     * @return
     */
    Result receiveRebate(int userId, String userPhone, List<Integer> batchIdList, String activityIdentification);

    /**
     * 校验该优惠券是否有效
     * @param order
     * @param rebateReceiveId
     * @return
     */
    Result validateRebate(Order order, int rebateReceiveId);
}
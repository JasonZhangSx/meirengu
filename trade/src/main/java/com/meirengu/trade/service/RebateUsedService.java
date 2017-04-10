package com.meirengu.trade.service;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.model.RebateUsed;
import com.meirengu.service.BaseService;

/**
 * RebateUsed服务接口 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateUsedService extends BaseService<RebateUsed>{

    /**
     * 优惠券的使用
     * @param rebateReceiveId
     * @param orderSn
     * @return
     */
    void rebateUse(int rebateReceiveId, String orderSn)  throws OrderException;
}

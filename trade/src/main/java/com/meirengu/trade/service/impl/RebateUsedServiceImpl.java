package com.meirengu.trade.service.impl;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.model.RebateUsed;
import com.meirengu.trade.service.RebateBatchService;
import com.meirengu.trade.service.RebateReceiveService;
import com.meirengu.trade.service.RebateUsedService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * RebateUsed服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateUsedServiceImpl extends BaseServiceImpl<RebateUsed> implements RebateUsedService{

    @Autowired
    private RebateReceiveService rebateReceiveService;

    @Autowired
    private RebateBatchService rebateBatchService;

    /**
     * 优惠券的使用
     * @param rebateReceiveId
     * @param orderSn
     * @return
     */
    public int rebateUse(int rebateReceiveId, String orderSn) {
        //修改用户领取记录表优惠券状态
        RebateReceive rebateReceive = rebateReceiveService.detail(rebateReceiveId);
        RebateReceive updateRebateReceive = new RebateReceive();
        updateRebateReceive.setId(rebateReceive.getId());
        updateRebateReceive.setStatus(2);//已使用
        int j = rebateReceiveService.update(updateRebateReceive);
        RebateBatch rebateBatch = rebateBatchService.detail(rebateReceive.getRebateBatchId());
        //新增优惠券使用记录
        RebateUsed rebateUsed = new RebateUsed();
        rebateUsed.setUserId(rebateReceive.getUserId());
        rebateUsed.setUserPhone(rebateReceive.getUserPhone());
        rebateUsed.setRebateSn(rebateReceive.getRebateSn());
        rebateUsed.setRebateBatchId(rebateReceive.getRebateBatchId());
        rebateUsed.setActivityIdentification(rebateReceive.getActivityIdentification());
        rebateUsed.setOrderSn(orderSn);
        rebateUsed.setRebateAmount(rebateBatch.getRebateAmount());
        rebateUsed.setUsedTime(new Date());
        int i = insert(rebateUsed);
        return i;
    }
}

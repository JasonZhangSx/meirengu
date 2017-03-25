package com.meirengu.trade.service.impl;
import com.meirengu.trade.dao.RebateDao;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.service.RebateService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Rebate服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateServiceImpl extends BaseServiceImpl<Rebate> implements RebateService{

    @Autowired
    private RebateDao rebateDao;

    /**
     * 根据优惠券号更新
     * @param rebate
     * @return
     */
    public int updateBySn(Rebate rebate) {
        return rebateDao.updateBySn(rebate);
    }
}

package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.dao.RebateUsedDao;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.model.RebateUsed;
import com.meirengu.trade.service.RebateBatchService;
import com.meirengu.trade.service.RebateReceiveService;
import com.meirengu.trade.service.RebateUsedService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * RebateUsed服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateUsedServiceImpl extends BaseServiceImpl<RebateUsed> implements RebateUsedService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RebateUsedServiceImpl.class);

    @Autowired
    private RebateReceiveService rebateReceiveService;

    @Autowired
    private RebateBatchService rebateBatchService;

    @Autowired
    private RebateUsedDao rebateUsedDao;

    /**
     * 优惠券的使用
     * @param rebateReceiveId
     * @param orderSn
     * @return
     */
    @Transactional
    public void rebateUse(int rebateReceiveId, String orderSn) throws OrderException {
        //修改用户领取记录表优惠券状态
        RebateReceive rebateReceive = rebateReceiveService.detail(rebateReceiveId);
        RebateReceive updateRebateReceive = new RebateReceive();
        updateRebateReceive.setId(rebateReceive.getId());
        updateRebateReceive.setStatus(Constant.REBATE_RECEIVE_USED);//已使用
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
        rebateUsed.setVerifyStatus(Constant.NO);
        int i = insert(rebateUsed);
        if (!(j == 1 && i == 1)) {
            throw new OrderException("优惠券使用失败，请重试", StatusCode.REBATE_USE_FAIL);
        }
    }

    /**
     * 根据条件进行分页查询
     * @param page
     * @param map
     * @return
     */
    @Override
    public Page getVerifyInfoByPage(Page page, Map map) {
        int startPos = page.getStartPos();
        int pageSize = page.getPageSize();
        RowBounds rowBounds = new RowBounds(startPos, pageSize);
        List<Map<String, Object>> aList = rebateUsedDao.getVerifyInfoByPage(map, rowBounds);
        int totalCount = rebateUsedDao.getVerifyInfoCount(map);
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        LOGGER.info(" page params is "+ JSON.toJSON(map));
        return page;
    }
    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    public Integer getVerifyInfoCount(Map map) {
        return rebateUsedDao.getVerifyInfoCount(map);
    }

//    /**
//     * 查询符合条件的记录数量
//     * @param map
//     * @return
//     */
//    public Integer getUsedCount(Map map){
//        return rebateUsedDao.getUsedCount(map);
//    }
}

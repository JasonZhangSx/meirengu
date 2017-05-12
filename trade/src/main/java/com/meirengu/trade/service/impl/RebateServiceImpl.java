package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.meirengu.model.Page;
import com.meirengu.trade.dao.RebateDao;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.service.RebateService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Rebate服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateServiceImpl extends BaseServiceImpl<Rebate> implements RebateService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RebateServiceImpl.class);

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

    /**
     * 根据条件进行分页
     * @param page
     * @param map
     * @return
     */
    @Override
    public Page getRebateFullInfoByPage(Page page, Map map) {
        int startPos = page.getStartPos();
        int pageSize = page.getPageSize();
        RowBounds rowBounds = new RowBounds(startPos, pageSize);
        List<Map<String, Object>> aList = rebateDao.getRebateFullInfoByPage(map, rowBounds);
        int totalCount = rebateDao.getRebateFullInfoCount(map);
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        LOGGER.info(" page params is "+ JSON.toJSON(map));
        return page;
    }
}

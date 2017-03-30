package com.meirengu.trade.dao;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * RebateBatchDao 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateBatchDao extends BaseDao<RebateBatch>{
    /**
     * 根据条件查询优惠券批次
     * @param paramMap
     * @return
     */
    List<RebateBatch> findByCondition(Map<String, Object> paramMap);
}

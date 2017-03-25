package com.meirengu.trade.dao;
import com.meirengu.trade.model.Rebate;
import com.meirengu.dao.BaseDao;

import java.util.List;

/**
 * RebateDao 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateDao extends BaseDao<Rebate>{

    /**
     * 批量插入数据
     * @param rebateList
     * @return
     */
    public int batchSave(List<Rebate> rebateList);
    /**
     * 根据优惠券号更新
     * @param rebate
     * @return
     */
    public int updateBySn(Rebate rebate);
}

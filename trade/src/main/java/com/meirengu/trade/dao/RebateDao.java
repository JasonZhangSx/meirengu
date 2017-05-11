package com.meirengu.trade.dao;
import com.meirengu.trade.model.Rebate;
import com.meirengu.dao.BaseDao;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

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
    /**
     * 根据条件进行分页
     * @param map
     * @param rowBounds
     * @return
     */
    public List<Map<String, Object>> getRebateFullInfoByPage(Map map, RowBounds rowBounds);
    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    public Integer getRebateFullInfoCount(Map map);
}

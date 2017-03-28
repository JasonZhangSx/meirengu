package com.meirengu.trade.dao;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.dao.BaseDao;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * RebateReceiveDao 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateReceiveDao extends BaseDao<RebateReceive>{

    /**
     * 获取优惠券信息(连表查询)
     * 根据条件进行分页
     * @param map
     * @param rowBounds
     * @return
     */
    public List<Map<String, Object>> getRebateInfoByPage(Map map, RowBounds rowBounds);

    /**
     * 获取优惠券信息(连表查询)
     * 根据条件获取总条数
     * @param map
     * @return
     */
    public Integer getRebateInfoCount(Map map);

}

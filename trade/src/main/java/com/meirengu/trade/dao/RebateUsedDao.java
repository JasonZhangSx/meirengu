package com.meirengu.trade.dao;
import com.meirengu.trade.model.RebateUsed;
import com.meirengu.dao.BaseDao;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * RebateUsedDao 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateUsedDao extends BaseDao<RebateUsed>{
    /**
     * 根据条件进行分页
     * @param map
     * @param rowBounds
     * @return
     */
    List<Map<String, Object>> getVerifyInfoByPage(Map map, RowBounds rowBounds);
    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    Integer getVerifyInfoCount(Map map);
//    /**
//     * 查询符合条件的记录数量
//     * @param map
//     * @return
//     */
//    Integer getUsedCount(Map map);
}

package com.meirengu.cf.dao;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.dao.BaseDao;
/**
 * ItemLevelDao 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface ItemLevelDao extends BaseDao<ItemLevel>{

    int updateNumber(ItemLevel itemLevel);

    /**
     * 通过项目id修改档位状态
     * @param itemLevel
     * @return
     */
    int updateStatusByItemId(ItemLevel itemLevel);
}

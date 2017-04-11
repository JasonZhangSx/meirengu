package com.meirengu.cf.dao;
import com.meirengu.cf.model.ItemCooperation;
import com.meirengu.dao.BaseDao;
/**
 * ItemCooperationDao 
 * @author 建新
 * @create Mon Apr 10 16:18:41 CST 2017
 */
public interface ItemCooperationDao extends BaseDao<ItemCooperation>{
    /**
     * 通过项目id获取合作信息
     * @param itemId
     * @return
     */
    ItemCooperation getByItemId(int itemId);
}

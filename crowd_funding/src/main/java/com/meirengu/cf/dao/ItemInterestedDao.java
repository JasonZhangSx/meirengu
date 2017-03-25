package com.meirengu.cf.dao;
import com.meirengu.cf.model.ItemInterested;
import com.meirengu.dao.BaseDao;
/**
 * ItemInterestedDao 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface ItemInterestedDao extends BaseDao<ItemInterested>{

    int updateStatus(ItemInterested itemInterested);

    ItemInterested detailByUserAndItem(ItemInterested itemInterested);
}

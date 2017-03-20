package com.meirengu.cf.dao;
import com.meirengu.cf.model.Item;
import com.meirengu.dao.BaseDao;
/**
 * ItemDao 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface ItemDao extends BaseDao<Item>{

    int updateStatus(Item item);

    int changeAmount(Item item);
}

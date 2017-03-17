package com.meirengu.cf.service.impl;
import com.meirengu.cf.dao.ItemDao;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.service.ItemService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Item服务实现层 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{

    @Autowired
    ItemDao itemDao;

    @Override
    public int updateStatus(Item item) {
        return itemDao.updateStatus(item);
    }
}

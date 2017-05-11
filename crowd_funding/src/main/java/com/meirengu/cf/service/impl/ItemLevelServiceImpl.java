package com.meirengu.cf.service.impl;
import com.meirengu.cf.dao.ItemDao;
import com.meirengu.cf.dao.ItemLevelDao;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.cf.service.ItemLevelService;
import com.meirengu.cf.service.ItemService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ItemLevel服务实现层 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
@Service
public class ItemLevelServiceImpl extends BaseServiceImpl<ItemLevel> implements ItemLevelService{

    @Autowired
    ItemLevelDao itemLevelDao;


    @Override
    public int updateNumber(ItemLevel itemLevel) {
        return itemLevelDao.updateNumber(itemLevel);
    }

    @Override
    public int updateStatusByItemId(ItemLevel itemLevel) {
        return itemLevelDao.updateStatusByItemId(itemLevel);
    }
}

package com.meirengu.cf.service.impl;
import com.meirengu.cf.dao.ItemCooperationDao;
import com.meirengu.cf.model.ItemCooperation;
import com.meirengu.cf.service.ItemCooperationService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ItemCooperation服务实现层 
 * @author 建新
 * @create Mon Apr 10 16:18:41 CST 2017
 */
@Service
public class ItemCooperationServiceImpl extends BaseServiceImpl<ItemCooperation> implements ItemCooperationService{

    @Autowired
    ItemCooperationDao itemCooperationDao;

    @Override
    public ItemCooperation getByItemId(int itemId) {
        return itemCooperationDao.getByItemId(itemId);
    }
}

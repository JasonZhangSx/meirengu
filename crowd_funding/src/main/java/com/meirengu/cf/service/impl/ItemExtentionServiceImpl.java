package com.meirengu.cf.service.impl;
import com.meirengu.cf.dao.ItemExtentionDao;
import com.meirengu.cf.model.ItemExtention;
import com.meirengu.cf.service.ItemExtentionService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ItemExtention服务实现层 
 * @author 建新
 * @create Tue May 23 14:41:52 CST 2017
 */
@Service
public class ItemExtentionServiceImpl extends BaseServiceImpl<ItemExtention> implements ItemExtentionService{

    @Autowired
    ItemExtentionDao itemExtentionDao;

    @Override
    public String getIdsByInvestorId(int leadInvestorId) {
        return itemExtentionDao.getIdsByInvestorId(leadInvestorId);
    }
}

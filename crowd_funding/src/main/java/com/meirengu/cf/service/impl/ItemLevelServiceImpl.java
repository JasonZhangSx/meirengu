package com.meirengu.cf.service.impl;
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
    ItemService itemService;


}

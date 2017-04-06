package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
import com.meirengu.cf.dao.ItemOperateRecordDao;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemOperateRecord;
import com.meirengu.cf.service.ItemOperateRecordService;
import com.meirengu.cf.service.ItemService;
import com.meirengu.exception.BusinessException;
import com.meirengu.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * ItemOperateRecord服务实现层 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
@Service
public class ItemOperateRecordServiceImpl extends BaseServiceImpl<ItemOperateRecord> implements ItemOperateRecordService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemOperateRecordServiceImpl.class);

    @Autowired
    private ItemOperateRecordDao itemOperateRecordDao;
    @Autowired
    private ItemService itemService;


}

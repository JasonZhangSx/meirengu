package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
import com.meirengu.cf.dao.ItemCooperationDao;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemCooperation;
import com.meirengu.cf.model.ItemOperateRecord;
import com.meirengu.cf.service.ItemCooperationService;
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
 * ItemCooperation服务实现层 
 * @author 建新
 * @create Mon Apr 10 16:18:41 CST 2017
 */
@Service
public class ItemCooperationServiceImpl extends BaseServiceImpl<ItemCooperation> implements ItemCooperationService{

    Logger logger = LoggerFactory.getLogger(ItemCooperationServiceImpl.class);

    @Autowired
    ItemCooperationDao itemCooperationDao;
    @Autowired
    ItemOperateRecordService itemOperateRecordService;
    @Autowired
    ItemService itemService;

    @Override
    public ItemCooperation getByItemId(int itemId) {
        return itemCooperationDao.getByItemId(itemId);
    }

    @Override
    @Transactional
    public void setCooperate(ItemCooperation cooperation) {

        try {
            //设置合作三步 1 添加合作数据 2 修改项目数据 3 添加合作记录
            if(cooperation == null){
                logger.warn(">>ItemCooperationServiceImpl.setCooperate cooperation is null");
            }
            int itemId = cooperation.getItemId();
            //1 添加合作数据
            int insertCooperationNum = this.insert(cooperation);
            if(insertCooperationNum != 1){
                logger.warn(">>ItemCooperationServiceImpl.setCooperate insert cooperation fail.....");
            }

            ItemOperateRecord itemOperateRecord = new ItemOperateRecord();
            itemOperateRecord.setItemId(itemId);
            itemOperateRecord.setOperateType(Constants.RECORD_OPERATION);
            itemOperateRecord.setOperateStatus(0);
            itemOperateRecord.setOperateRemark("");
            itemOperateRecord.setOperateTime(new Date());
            itemOperateRecord.setOperateAccount(cooperation.getOperateAccount());

            Item item = new Item();
            item.setItemId(itemId);
            item.setItemStatus(Constants.ITEM_REVIEW_VERIFY);
            //修改项目状态
            int updateNum = itemService.update(item);
            if(updateNum != 1){
                logger.warn(">>ItemCooperationServiceImpl.setCooperate update item status fail... return updateNum is {}", updateNum);
                throw new BusinessException("ItemCooperationServiceImpl.setCooperate update item status fail...");
            }
            //插入审核记录
            int insertNum = itemOperateRecordService.insert(itemOperateRecord);
            if(insertNum != 1){
                logger.warn(">>ItemCooperationServiceImpl.setCooperate insert operate record fail... return insertNum is {}", insertNum);
                throw new BusinessException("ItemCooperationServiceImpl.setCooperate insert operate record fail...");
            }

        }catch (Exception e){
            throw new BusinessException("ItemOperateRecordServiceImpl.setCooperate throw exception:",e);
        }
    }
}

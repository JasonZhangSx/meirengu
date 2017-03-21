package com.meirengu.cf.service.impl;
import com.meirengu.cf.dao.ItemDao;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.cf.service.ItemLevelService;
import com.meirengu.cf.service.ItemService;
import com.meirengu.exception.BusinessException;
import com.meirengu.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Item服务实现层 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemLevelService itemLevelService;

    @Override
    public int updateStatus(Item item) {
        return itemDao.updateStatus(item);
    }

    @Override
    public boolean changeAmount(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum,
                                       BigDecimal appointAmount, BigDecimal completedAmount) {
        String errorMsg;
        try {

            ItemLevel il = itemLevelService.detail(levelId);
            if(il == null){
                LOGGER.warn(">> the level does not exit.... id : ", levelId);
                return false;
            }

            BigDecimal levelAmount1 = il.getLevelAmount();
            if(levelAmount.compareTo(levelAmount1) != 0){
                LOGGER.warn(">> two level amount does not equal... param amount is {}, query amount is {}",
                        new Object[]{levelAmount, levelAmount1});
                return false;
            }

            BigDecimal totalAmount = levelAmount.multiply(new BigDecimal(itemNum)); //levelAmount x itemNum
            int compareToReturnNum = -1;
            if(appointAmount != null && completedAmount == null){
                compareToReturnNum = totalAmount.compareTo(appointAmount);
            }else if(appointAmount == null && completedAmount != null){
                compareToReturnNum = totalAmount.compareTo(completedAmount);
            }else {
                LOGGER.warn(">> appointAmount and completedAmount error... appointAmount: {}, completedAmount: {}",
                        new Object[]{appointAmount, completedAmount});
                return false;
            }

            if(compareToReturnNum != 0){
                LOGGER.warn(">> totalAmount does not equal levelAmount x itemNum... totalAmount: {}, levelAmount: {}, itemNum: {}",
                        new Object[]{totalAmount, levelAmount, itemNum});
                return false;
            }

            Item item = new Item();
            item.setItemId(itemId);
            item.setAppointAmount(appointAmount);
            item.setCompletedAmount(completedAmount);
            int i = itemDao.changeAmount(item);
            if(i == 0){
                return true;
            }else {
                LOGGER.warn(">> update amount fail....");
                return false;
            }
        }catch (Exception e){
            errorMsg = ">> change amount throw exception: {}";
            LOGGER.error(errorMsg, e);
            throw new BusinessException(errorMsg, e);
        }
    }

    @Override
    public Map<String, Object> moreDetail(int id) {
        return itemDao.moreDetail(id);
    }

}

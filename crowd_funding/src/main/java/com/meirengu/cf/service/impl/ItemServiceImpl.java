package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public int changeAmount(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum,
                                       BigDecimal appointAmount, BigDecimal completedAmount) {
        //0 失败  1 成功  2  此时状态为已约满  3 此时状态为已完成  4 档位信息为空  5 参数金额和档位金额不同
        //6 itemNum大于可预约数  7 itemNum大于可购买金额  8 预约金额和购买金额比填一个  9 总金额不等于单项金额和项目数量的乘积
        //10
        String errorMsg;
        int updateLevel = 0;
        int type = 0;
        //1.修改订单的预约/已筹金额  2.档位份数是否不足 3.修改档位预约份数/完成份数
        try {
            //1.修改订单的预约/已筹金额
            ItemLevel il = itemLevelService.detail(levelId);
            if(il == null){
                LOGGER.warn(">> the level does not exit.... id : ", levelId);
                return 4;
            }
            int levelStatus = il.getLevelStatus();

            BigDecimal levelAmount1 = il.getLevelAmount();
            if(levelAmount.compareTo(levelAmount1) != 0){
                LOGGER.warn(">> two level amount does not equal... param amount is {}, query amount is {}",
                        new Object[]{levelAmount, levelAmount1});
                return 5;
            }

            BigDecimal totalAmount = levelAmount.multiply(new BigDecimal(itemNum)); //levelAmount x itemNum
            int compareToReturnNum = -1;
            int bookNumber = il.getBookNumber();
            int completedNumber = il.getCompletedNumber();
            int totalNumber = il.getTotalNumber();
            if(appointAmount != null && completedAmount == null){ //预约
                type = 2;
                //已约满
                if(levelStatus != Constants.LEVEL_APPOINTING){
                    LOGGER.warn(">> the level status is appoint full... status : {}", levelStatus);
                    return 2;
                }
                if(bookNumber + itemNum > totalNumber){
                    LOGGER.warn(">> itemNum more than appoint available...  bookNumber: {}, itemNum: {}, totalNumber: {}",
                            new Object[]{bookNumber, itemNum, totalNumber});
                    return 6;
                }else if(bookNumber + itemNum == totalNumber){
                    updateLevel = Constants.LEVEL_CANDIDITING;
                }
                compareToReturnNum = totalAmount.compareTo(appointAmount);
            }else if(appointAmount == null && completedAmount != null){ //完成
                type = 1;
                //已完成
                if(levelStatus != Constants.LEVEL_CROWDING){
                    LOGGER.warn(">> the level status is completed... status : {}", levelStatus);
                    return 3;
                }
                if(completedNumber + itemNum > totalNumber){
                    LOGGER.warn(">> itemNum more than completed available...  completedNumber: {}, itemNum: {}, totalNumber: {}",
                            new Object[]{completedNumber, itemNum, totalNumber});
                    return 7;
                }else if(completedNumber + itemNum == totalNumber){
                    updateLevel = Constants.LEVEL_COMPLETED;
                }
                compareToReturnNum = totalAmount.compareTo(completedAmount);
            }else {
                LOGGER.warn(">> appointAmount and completedAmount error... appointAmount: {}, completedAmount: {}",
                        new Object[]{appointAmount, completedAmount});
                return 8;
            }

            if(compareToReturnNum != 0){
                LOGGER.warn(">> totalAmount does not equal levelAmount x itemNum... totalAmount: {}, levelAmount: {}, itemNum: {}",
                        new Object[]{totalAmount, levelAmount, itemNum});
                return 9;
            }

            Item item = new Item();
            item.setItemId(itemId);
            item.setAppointAmount(appointAmount);
            item.setCompletedAmount(completedAmount);
            int i = itemDao.changeAmount(item);
            if(i != 1){
                LOGGER.warn(">> update amount fail....");
                throw new BusinessException("update amount fail....");
            }

            ItemLevel levelParam = new ItemLevel();
            levelParam.setLevelId(levelId);

            if(type == 1){
                levelParam.setCompletedNumber(completedNumber + itemNum);
            }else if(type == 2){
                levelParam.setBookNumber(bookNumber + itemNum);
            }

            if(updateLevel != 0){
                levelParam.setLevelStatus(updateLevel);
            }
            int j = itemLevelService.updateNumber(levelParam);
            if(j != 1){
                LOGGER.warn(">> update number fail....");
                throw new BusinessException("update number fail....");
            }

            return 1;
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

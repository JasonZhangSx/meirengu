package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
import com.meirengu.cf.dao.ItemDao;
import com.meirengu.cf.model.Item;
import com.meirengu.cf.model.ItemLevel;
import com.meirengu.cf.model.ItemOperateRecord;
import com.meirengu.cf.service.ItemLevelService;
import com.meirengu.cf.service.ItemOperateRecordService;
import com.meirengu.cf.service.ItemService;
import com.meirengu.common.StatusCode;
import com.meirengu.exception.BusinessException;
import com.meirengu.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
    @Autowired
    ItemOperateRecordService itemOperateRecordService;

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
                if(levelStatus == Constants.LEVEL_APPOINT_FULL){
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
                if(levelStatus == Constants.LEVEL_COMPLETED){
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

    /**
     * 订单失效档位信息回滚
     * @param itemId
     * @param levelAmount
     * @param levelId
     * @param itemNum
     * @param completedAmount
     * @return
     */
    @Override
    public int levelRollback(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum, BigDecimal completedAmount){

        try {
            //1.修改订单的预约/已筹金额
            ItemLevel il = itemLevelService.detail(levelId);
            if(il == null){
                LOGGER.warn(">> the level does not exit.... id : ", levelId);
                return StatusCode.ITEM_LEVEL_NULL;
            }
            int levelStatus = il.getLevelStatus();

            BigDecimal levelAmount1 = il.getLevelAmount();
            if(levelAmount.compareTo(levelAmount1) != 0){
                LOGGER.warn(">> two level amount does not equal... param amount is {}, query amount is {}",
                        new Object[]{levelAmount, levelAmount1});
                return StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH;
            }

            BigDecimal totalAmount = levelAmount.multiply(new BigDecimal(itemNum)); //levelAmount x itemNum
            int compareToReturnNum = -1;
            int completedNumber = il.getCompletedNumber();
            int currentNumber = completedNumber - itemNum;
            //先比较已完成的数量和要回滚的数量
            if(currentNumber < 0){
                LOGGER.warn(">> two level amount does not equal... param itemNum is {}, completedNumber is {}", new Object[]{itemNum, completedNumber});
                return StatusCode.LEVEL_ROLLBACK_OUT_NUMBER;
            }

            compareToReturnNum = totalAmount.compareTo(completedAmount);

            if(compareToReturnNum != 0){
                LOGGER.warn(">> totalAmount does not equal levelAmount x itemNum... totalAmount: {}, levelAmount: {}, itemNum: {}",
                        new Object[]{totalAmount, levelAmount, itemNum});
                return StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR;
            }

            Item item = new Item();
            item.setItemId(itemId);
            item.setCompletedAmount(totalAmount.multiply(new BigDecimal(-1)));
            int i = itemDao.changeAmount(item);
            if(i != 1){
                LOGGER.warn(">> update amount fail....");
                throw new BusinessException("update amount fail....");
            }

            ItemLevel levelParam = new ItemLevel();
            levelParam.setLevelId(levelId);

            levelParam.setCompletedNumber(currentNumber);

            if(levelStatus == Constants.LEVEL_COMPLETED){
                levelParam.setLevelStatus(Constants.LEVEL_CROWDING);
            }
            int j = itemLevelService.updateNumber(levelParam);
            if(j != 1){
                LOGGER.warn(">> update number fail....");
                throw new BusinessException("update number fail....");
            }

            return StatusCode.OK;
        }catch (Exception e){
            String errorMsg = ">> change amount throw exception: {}";
            LOGGER.error(errorMsg, e);
            throw new BusinessException(errorMsg, e);
        }
    }

    @Override
    public Map<String, Object> moreDetail(int id) {
        return itemDao.moreDetail(id);
    }

    @Override
    @Transactional
    public void verify(int itemId, int operateStatus, String operateRemark, String operateAccount) {
        //1插入审核记录 2修改项目状态
        ItemOperateRecord itemOperateRecord = new ItemOperateRecord();
        itemOperateRecord.setItemId(itemId);
        itemOperateRecord.setOperateType(Constants.RECORD_FIRST_VERIFY);
        itemOperateRecord.setOperateStatus(operateStatus);
        itemOperateRecord.setOperateRemark(operateRemark);
        itemOperateRecord.setOperateTime(new Date());
        itemOperateRecord.setOperateAccount(operateAccount);

        Item item = new Item();
        item.setItemId(itemId);
        if(operateStatus == Constants.STATUS_YES){
            item.setItemStatus(Constants.ITEM_FIRST_VERIFY_SUCCESS);
        }else if(operateStatus == Constants.STATUS_NO){
            item.setItemStatus(Constants.ITEM_FIRST_VERIFY_FAIL);
        }else{
            LOGGER.warn(">>ItemOperateRecordServiceImpl.verify param operateStatus not invalid...");
            throw new BusinessException("ItemOperateRecordServiceImpl.verify param operateStatus not invalid...");
        }

        try {
            int insertNum = itemOperateRecordService.insert(itemOperateRecord);
            if(insertNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.verify insert operate record fail... return insertNum is {}", insertNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.verify insert operate record fail...");
            }

            int updateNum = super.update(item);
            if(updateNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.verify update item status fail... return updateNum is {}", updateNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.verify update item status fail...");
            }
        }catch (Exception e){
            throw new BusinessException("ItemOperateRecordServiceImpl.verify throw exception:",e);
        }
    }

    @Override
    public void setCooperate(int itemId, int operateStatus, String operateRemark, String operateAccount) {
        //1插入审核记录 2修改项目状态
        ItemOperateRecord itemOperateRecord = new ItemOperateRecord();
        itemOperateRecord.setItemId(itemId);
        itemOperateRecord.setOperateType(Constants.RECORD_OPERATION);
        itemOperateRecord.setOperateStatus(operateStatus);
        itemOperateRecord.setOperateRemark(operateRemark);
        itemOperateRecord.setOperateTime(new Date());
        itemOperateRecord.setOperateAccount(operateAccount);

        Item item = new Item();
        item.setItemId(itemId);
        if(operateStatus == Constants.STATUS_YES){
            item.setItemStatus(Constants.ITEM_FIRST_VERIFY_SUCCESS);
        }else if(operateStatus == Constants.STATUS_NO){
            item.setItemStatus(Constants.ITEM_FIRST_VERIFY_FAIL);
        }else{
            LOGGER.warn(">>ItemOperateRecordServiceImpl.verify param operateStatus not invalid...");
            throw new BusinessException("ItemOperateRecordServiceImpl.verify param operateStatus not invalid...");
        }

        try {
            int insertNum = itemOperateRecordService.insert(itemOperateRecord);
            if(insertNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.verify insert operate record fail... return insertNum is {}", insertNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.verify insert operate record fail...");
            }

            int updateNum = super.update(item);
            if(updateNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.verify update item status fail... return updateNum is {}", updateNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.verify update item status fail...");
            }
        }catch (Exception e){
            throw new BusinessException("ItemOperateRecordServiceImpl.verify throw exception:",e);
        }
    }

    @Override
    @Transactional
    public void review(int itemId, int operateStatus, String operateRemark, String operateAccount) {
        //1插入审核记录 2修改项目状态
        ItemOperateRecord itemOperateRecord = new ItemOperateRecord();
        itemOperateRecord.setItemId(itemId);
        itemOperateRecord.setOperateType(Constants.RECORD_REVIEW_VERIFY);
        itemOperateRecord.setOperateStatus(operateStatus);
        itemOperateRecord.setOperateRemark(operateRemark);
        itemOperateRecord.setOperateTime(new Date());
        itemOperateRecord.setOperateAccount(operateAccount);

        Item item = new Item();
        item.setItemId(itemId);
        if(operateStatus == Constants.STATUS_YES){
            item.setItemStatus(Constants.ITEM_REVIEW_VERIFY_SUCCESS);
        }else if(operateStatus == Constants.STATUS_NO){
            item.setItemStatus(Constants.ITEM_REVIEW_VERIFY_FAIL);
        }else{
            LOGGER.warn(">>ItemOperateRecordServiceImpl.verify param operateStatus not invalid...");
            throw new BusinessException("ItemOperateRecordServiceImpl.verify param operateStatus not invalid...");
        }

        try {
            int insertNum = itemOperateRecordService.insert(itemOperateRecord);
            if(insertNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.review insert operate record fail... return insertNum is {}", insertNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.review insert operate record fail...");
            }

            int updateNum = super.update(item);
            if(updateNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.review update item status fail... return updateNum is {}", updateNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.review update item status fail...");
            }
        }catch (Exception e){
            throw new BusinessException("ItemOperateRecordServiceImpl.review throw exception:",e);
        }
    }

    @Override
    public boolean publish() {
        return false;
    }

}

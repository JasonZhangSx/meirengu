package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
import com.meirengu.cf.dao.ItemDao;
import com.meirengu.cf.model.*;
import com.meirengu.cf.service.*;
import com.meirengu.common.StatusCode;
import com.meirengu.exception.BusinessException;
import com.meirengu.model.Page;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    PartnerService partnerService;
    @Autowired
    ItemCooperationService itemCooperationService;

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
            //档位份数校验
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

            //修改档位份数
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

            //修改项目预约金额（已筹不修改，下单成功后修改）
            if(type == 2){
                Item item = new Item();
                item.setItemId(itemId);
                item.setAppointAmount(appointAmount);
                //item.setCompletedAmount(completedAmount);
                int i = itemDao.changeAmount(item);
                if(i != 1){
                    LOGGER.warn(">> update amount fail....");
                    throw new BusinessException("update amount fail....");
                }
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
    @Transactional
    public int completedRollback(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum, BigDecimal completedAmount, Integer operateStatus){

        try {
            //1.修改订单的已筹金额
            ItemLevel il = itemLevelService.detail(levelId);
            if(il == null){
                LOGGER.warn(">>ItemServiceImpl.completedRollback the level does not exit.... id : ", levelId);
                return StatusCode.ITEM_LEVEL_NULL;
            }
            int levelStatus = il.getLevelStatus();

            BigDecimal levelAmount1 = il.getLevelAmount();
            if(levelAmount.compareTo(levelAmount1) != 0){
                LOGGER.warn(">>ItemServiceImpl.completedRollback two level amount does not equal... param amount is {}, query amount is {}",
                        new Object[]{levelAmount, levelAmount1});
                return StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH;
            }

            BigDecimal totalAmount = levelAmount.multiply(new BigDecimal(itemNum)); //levelAmount x itemNum
            int compareToReturnNum = -1;
            int totalNumber = il.getTotalNumber();
            int completedNumber = il.getCompletedNumber();
            int currentNumber = completedNumber - itemNum;
            //先比较已完成的数量和要回滚的数量
            if(currentNumber < 0){
                LOGGER.warn(">>ItemServiceImpl.completedRollback two level amount does not equal... param itemNum is {}, completedNumber is {}", new Object[]{itemNum, completedNumber});
                return StatusCode.LEVEL_ROLLBACK_OUT_NUMBER;
            }

            compareToReturnNum = totalAmount.compareTo(completedAmount);

            if(compareToReturnNum != 0){
                LOGGER.warn(">>ItemServiceImpl.completedRollback totalAmount does not equal levelAmount x itemNum... totalAmount: {}, levelAmount: {}, itemNum: {}",
                        new Object[]{totalAmount, levelAmount, itemNum});
                return StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR;
            }

            //已筹金额回滚
            /*Item item = new Item();
            item.setItemId(itemId);
            item.setCompletedAmount(totalAmount.multiply(new BigDecimal(-1)));
            int i = itemDao.changeAmount(item);
            if(i != 1){
                LOGGER.warn(">> update amount fail....");
                throw new BusinessException("update amount fail....");
            }*/

            ItemLevel levelParam = new ItemLevel();

            //operateStatus == 1 订单在预约状态下失效通知回滚预约金额和预约的档位份数
            if(operateStatus == 1){

                int appointNumber = il.getBookNumber();
                int currentAppointNumber = appointNumber - itemNum;
                //将预约的档位份数减回去
                levelParam.setBookNumber(currentAppointNumber);
                if(totalNumber > currentAppointNumber){
                    levelParam.setLevelStatus(Constants.LEVEL_APPOINTING);
                }

                Item item = new Item();
                item.setItemId(itemId);
                item.setAppointAmount(totalAmount.multiply(new BigDecimal(-1)));
                int i = itemDao.changeAmount(item);
                if(i != 1){
                    LOGGER.warn(">>ItemServiceImpl.completedRollback update appoint amount fail....");
                    throw new BusinessException("ItemServiceImpl.completedRollback update appoint amount fail....");
                }

            }else if(operateStatus == 2 ){  // operateStatus == 2 订单在通知不需要修改预约状态的时候，
                if(totalNumber > currentNumber){
                    levelParam.setLevelStatus(Constants.LEVEL_CROWDING);
                }
            }

            levelParam.setLevelId(levelId);
            levelParam.setCompletedNumber(currentNumber);


            int j = itemLevelService.updateNumber(levelParam);
            if(j != 1){
                LOGGER.warn(">>ItemServiceImpl.completedRollback update number fail....");
                throw new BusinessException("update number fail....");
            }

            return StatusCode.OK;
        }catch (Exception e){
            String errorMsg = ">>ItemServiceImpl.completedRollback change amount throw exception: {}";
            LOGGER.error(errorMsg, e);
            throw new BusinessException(errorMsg, e);
        }
    }

    @Override
    @Transactional
    public int appointRollback(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum, BigDecimal
            appointAmount) {
        try {
            //1.修改订单的预约金额
            ItemLevel il = itemLevelService.detail(levelId);
            if(il == null){
                LOGGER.warn(">>ItemServiceImpl.appointRollback the level does not exit.... id : ", levelId);
                return StatusCode.ITEM_LEVEL_NULL;
            }
            int levelStatus = il.getLevelStatus();

            BigDecimal levelAmount1 = il.getLevelAmount();
            if(levelAmount.compareTo(levelAmount1) != 0){
                LOGGER.warn(">>ItemServiceImpl.appointRollback two level amount does not equal... param amount is {}, query amount is {}",
                        new Object[]{levelAmount, levelAmount1});
                return StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH;
            }

            BigDecimal totalAmount = levelAmount.multiply(new BigDecimal(itemNum)); //levelAmount x itemNum
            int compareToReturnNum = -1;
            int bookNumber = il.getBookNumber();
            int currentNumber = bookNumber - itemNum;
            //先比较已完成的数量和要回滚的数量
            if(currentNumber < 0){
                LOGGER.warn(">>ItemServiceImpl.appointRollback two level amount does not equal... param itemNum is {}, bookNumber is {}", new Object[]{itemNum, bookNumber});
                return StatusCode.LEVEL_ROLLBACK_OUT_NUMBER;
            }

            compareToReturnNum = totalAmount.compareTo(appointAmount);

            if(compareToReturnNum != 0){
                LOGGER.warn(">>ItemServiceImpl.appointRollback totalAmount does not equal levelAmount x itemNum... totalAmount: {}, levelAmount: {}, itemNum: {}",
                        new Object[]{totalAmount, levelAmount, itemNum});
                return StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR;
            }

            Item item = new Item();
            item.setItemId(itemId);
            item.setAppointAmount(totalAmount.multiply(new BigDecimal(-1)));
            int i = itemDao.changeAmount(item);
            if(i != 1){
                LOGGER.warn(">>ItemServiceImpl.appointRollback update amount fail....");
                throw new BusinessException("ItemServiceImpl.appointRollback update amount fail....");
            }

            ItemLevel levelParam = new ItemLevel();
            levelParam.setLevelId(levelId);

            levelParam.setBookNumber(currentNumber);
            levelParam.setLevelStatus(Constants.LEVEL_APPOINTING);

            int j = itemLevelService.updateNumber(levelParam);
            if(j != 1){
                LOGGER.warn(">>ItemServiceImpl.appointRollback update number fail....");
                throw new BusinessException("ItemServiceImpl.appointRollback update number fail....");
            }

            return StatusCode.OK;
        }catch (Exception e){
            String errorMsg = ">>ItemServiceImpl.appointRollback change amount throw exception: {}";
            LOGGER.error(errorMsg, e);
            throw new BusinessException(errorMsg, e);
        }
    }

    @Override
    public int refund(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer levelNum, BigDecimal
            refundAmount) {
        try {
            BigDecimal totalAmount = levelAmount.multiply(new BigDecimal(levelNum)); //levelAmount x itemNum
            //计算一下传过来的总金额与单价*数量是否相同
            int compareToReturnNum = totalAmount.compareTo(refundAmount);

            if(compareToReturnNum != 0){
                LOGGER.warn(">>ItemServiceImpl.refund totalAmount does not equal levelAmount x itemNum... totalAmount: {}, levelAmount: {}, itemNum: {}",
                        new Object[]{totalAmount, levelAmount, levelNum});
                return StatusCode.ITEM_LEVEL_TOTAL_AMOUNT_ERROR;
            }

            //查询项目信息
            Item item = itemDao.detail(itemId);

            //查询档位信息
            ItemLevel il = itemLevelService.detail(levelId);
            if(il == null){
                LOGGER.warn(">>ItemServiceImpl.refund the level does not exit.... id : ", levelId);
                return StatusCode.ITEM_LEVEL_NULL;
            }
            int levelStatus = il.getLevelStatus();
            int totalNumber = il.getTotalNumber();

            BigDecimal levelAmount1 = il.getLevelAmount();
            if(levelAmount.compareTo(levelAmount1) != 0){
                LOGGER.warn(">>ItemServiceImpl.refund two level amount does not equal... param amount is {}, query amount is {}",
                        new Object[]{levelAmount, levelAmount1});
                return StatusCode.ITEM_LEVEL_AMOUNT_NOT_MATCH;
            }

            int completedNumber = il.getCompletedNumber();
            int currentNumber = completedNumber - levelNum;
            //比较已完成的数量和要回滚的数量
            if(currentNumber < 0){
                LOGGER.warn(">>ItemServiceImpl.refund two level amount does not equal... param itemNum is {}, completedNumber is {}", new Object[]{levelNum, completedNumber});
                return StatusCode.LEVEL_ROLLBACK_OUT_NUMBER;
            }
            int appointNumber = il.getBookNumber();
            int aCurrentNumber = appointNumber - levelNum;
            //比较已预约的数量和要回滚的数量
            if(aCurrentNumber < 0){
                LOGGER.warn(">>ItemServiceImpl.refund two level amount does not equal... param itemNum is {}, appointNumber is {}", new Object[]{levelNum, appointNumber});
                return StatusCode.LEVEL_ROLLBACK_OUT_NUMBER;
            }

            //已筹金额和预约金额回滚,项目状态不用改变
            Item itemParams = new Item();
            itemParams.setItemId(itemId);
            itemParams.setCompletedAmount(totalAmount.multiply(new BigDecimal(-1)));
            itemParams.setAppointAmount(totalAmount.multiply(new BigDecimal(-1)));
            int i = itemDao.changeAmount(itemParams);
            if(i != 1){
                LOGGER.warn(">> update amount fail....");
                throw new BusinessException("update amount fail....");
            }

            int itemStatus = item.getItemStatus();
            //档位已筹数量和预约数量回滚，以及档位状态的变化（项目状态是档位状态的前提）
            ItemLevel levelParam = new ItemLevel();

            levelParam.setLevelId(levelId);
            levelParam.setCompletedNumber(currentNumber);
            levelParam.setBookNumber(aCurrentNumber);
            //预热状态时
            if(itemStatus == Constants.ITEM_PERHEARTING){
                if(totalNumber > aCurrentNumber){
                    levelParam.setLevelStatus(Constants.LEVEL_APPOINTING);
                }else{
                    levelParam.setLevelStatus(Constants.LEVEL_CANDIDITING);
                }
            }else if(itemStatus == Constants.ITEM_CROWDING){ //认筹状态时
                if(totalNumber > currentNumber){
                    levelParam.setLevelStatus(Constants.LEVEL_CROWDING);
                }else{
                    levelParam.setLevelStatus(Constants.LEVEL_COMPLETED);
                }
            }else if(itemStatus == Constants.ITEM_COMPLETED){ //完成状态时

            }

            int j = itemLevelService.updateNumber(levelParam);
            if(j != 1){
                LOGGER.warn(">>ItemServiceImpl.refund update number fail....");
                throw new BusinessException("update number fail....");
            }

            return StatusCode.OK;
        }catch (Exception e){
            String errorMsg = ">>ItemServiceImpl.refund change amount throw exception: {}";
            LOGGER.error(errorMsg, e);
            throw new BusinessException(errorMsg, e);
        }
    }

    @Override
    public Map<String, Object> moreDetail(int id) {
        return itemDao.moreDetail(id);
    }

    @Override
    public Map<String, Object> getBankAndCommission(Integer itemId) {

        Map<String, Object> map = new HashMap<>();
        if(itemId != null && itemId != 0){
            Item item = detail(itemId);
            LOGGER.info(">>ItemServiceImpl.getBankAndCommission get item is {}", item);
            if(item != null){
                BigDecimal completedAmount = item.getCompletedAmount();
                int type = item.getTypeId();
                map.put("type", type);
                Map<String, Object> params = new HashMap<>();
                params.put("itemId", itemId);
                List<Map<String, Object>> list = itemLevelService.getList(params);
                map.put("levelList", list);

                int partnerId = item.getPartnerId();
                Partner partner = partnerService.detail(partnerId);
                LOGGER.info(">>ItemServiceImpl.getBankAndCommission get partner is {}", partner);
                if(partner != null){
                    map.put("bankName", partner.getBankName());
                    map.put("bankAccount", partner.getBankAccount());
                    map.put("bankCard", partner.getBankCard());
                    ItemCooperation cooperation = itemCooperationService.detail(itemId);
                    LOGGER.info(">>ItemServiceImpl.getBankAndCommission get cooperation is {}", cooperation);
                    if(cooperation != null){
                        BigDecimal commissionRate = cooperation.getCommissionRate();
                        BigDecimal guaranteeRate = cooperation.getGuaranteeRate();
                        map.put("commissionRate", commissionRate);
                        map.put("guaranteeRate", guaranteeRate);
                        BigDecimal commissionAmount = completedAmount.multiply(commissionRate.multiply(new BigDecimal(0.01)));
                        BigDecimal guaranteeAmount = completedAmount.multiply(guaranteeRate.multiply(new BigDecimal(0.01)));
                        map.put("commissionAmount", commissionAmount.intValue());
                        map.put("guaranteeAmount", guaranteeAmount.intValue());
                        map.put("prepaidBonus", cooperation.getPrepaidBonus());
                        map.put("loanMode", cooperation.getLoanMode());
                        map.put("firstRatio", cooperation.getFirstRatio());
                        return map;
                    }
                }
            }
        }
        LOGGER.info(">>ItemServiceImpl.getBankAndCommission return null");
        return null;
    }

    @Override
    public void updateItemCompleteAmount(Integer itemId, BigDecimal amount) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setCompletedAmount(amount);
        int i = itemDao.changeAmount(item);
        if(i != 1){
            LOGGER.warn(">> ItemServiceImpl.updateItemCompleteAmount fail....");
            throw new BusinessException("ItemServiceImpl.updateItemCompleteAmount fail....");
        }
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
    @Transactional
    public void setCooperate(ItemCooperation itemCooperation) {
        if(itemCooperation != null){
            //1 新增设置合作  2  修改项目状态  3 插入审核记录
            ItemOperateRecord itemOperateRecord = new ItemOperateRecord();
            itemOperateRecord.setItemId(itemCooperation.getItemId());
            itemOperateRecord.setOperateType(Constants.RECORD_OPERATION);
            itemOperateRecord.setOperateStatus(Constants.STATUS_YES);
            itemOperateRecord.setOperateRemark("");
            itemOperateRecord.setOperateTime(new Date());
            itemOperateRecord.setOperateAccount(itemCooperation.getOperateAccount());

            Item item = new Item();
            item.setItemId(itemCooperation.getItemId());
            item.setItemStatus(Constants.ITEM_REVIEW_VERIFY);

            try {

                int cooperationInsertNum = itemCooperationService.insert(itemCooperation);
                if(cooperationInsertNum != 1){
                    LOGGER.warn(">>ItemOperateRecordServiceImpl.setCooperate update item status fail... return updateNum is {}", cooperationInsertNum);
                    throw new BusinessException("ItemOperateRecordServiceImpl.setCooperate update item status fail...");
                }

                int updateNum = super.update(item);
                if(updateNum != 1){
                    LOGGER.warn(">>ItemOperateRecordServiceImpl.setCooperate update item status fail... return updateNum is {}", updateNum);
                    throw new BusinessException("ItemOperateRecordServiceImpl.setCooperate update item status fail...");
                }

                int insertNum = itemOperateRecordService.insert(itemOperateRecord);
                if(insertNum != 1){
                    LOGGER.warn(">>ItemOperateRecordServiceImpl.setCooperate insert operate record fail... return insertNum is {}", insertNum);
                    throw new BusinessException("ItemOperateRecordServiceImpl.setCooperate insert operate record fail...");
                }
            }catch (Exception e){
                throw new BusinessException("ItemOperateRecordServiceImpl.verify throw exception:",e);
            }
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
    @Transactional
    public void publish(int itemId, Date appointDate, int type, String operateAccount) {

        try{
            //发布需要三步，1.修改档位状态 2.修改项目状态和起始时间  3.审核记录
            Item item = this.detail(itemId);
            if(item == null){
                LOGGER.warn(">>ItemServiceImpl.publish item is null.. itemId: {}", itemId);
                throw new BusinessException("ItemServiceImpl.publisthrow get item is null");
            }

            Item params = new Item();
            params.setItemId(itemId);

            int preheatingDays = item.getPreheatingDays() == null ? 0 : item.getPreheatingDays();
            int crowdDays = item.getCrowdDays() == null ? 0 : item.getCrowdDays();

            if(appointDate == null){
                appointDate = new Date();
            }

            String baseDate = DateAndTime.dateFormat(appointDate, "yyyy-MM-dd HH:mm:ss");

            Date preheatingEndTime = DateAndTime.convertStringToDate(DateAndTime.dateAdd("dd", baseDate, preheatingDays));
            params.setPreheatingStartTime(appointDate);
            params.setPreheatingEndTime(preheatingEndTime);
            params.setCrowdStartTime(preheatingEndTime);
            Date crowdEndTime = DateAndTime.convertStringToDate(DateAndTime.dateAdd("dd", baseDate, preheatingDays+crowdDays));
            params.setCrowdEndTime(crowdEndTime);

            //type 1为预约 2为立即
            if(type == 1){
                //暂时这样
                int updateNum = 0;
                ItemLevel itemLevel = new ItemLevel();
                //预约期为0，就直接跳过预约进入众筹
                if(preheatingDays == 0){
                    params.setItemStatus(Constants.ITEM_CROWDING);
                    itemLevel.setLevelStatus(Constants.LEVEL_CROWDING);
                }else {
                    params.setItemStatus(Constants.ITEM_PERHEARTING);
                    itemLevel.setLevelStatus(Constants.LEVEL_APPOINTING);
                }

                itemLevel.setItemId(itemId);
                itemLevel.setOperateAccount(operateAccount);
                updateNum = itemLevelService.updateStatusByItemId(itemLevel);

                if(updateNum <= 0){
                    LOGGER.warn(">>ItemServiceImpl.publish item is null.. itemId: {}", itemId);
                    throw new BusinessException("ItemServiceImpl.publisthrow exception");
                }
            }else if(type == 2){
                int updateNum = 0;
                ItemLevel itemLevel = new ItemLevel();
                //预约期为0，就直接跳过预约进入众筹
                if(preheatingDays == 0){
                    params.setItemStatus(Constants.ITEM_CROWDING);
                    itemLevel.setLevelStatus(Constants.LEVEL_CROWDING);
                }else {
                    params.setItemStatus(Constants.ITEM_PERHEARTING);
                    itemLevel.setLevelStatus(Constants.LEVEL_APPOINTING);
                }

                itemLevel.setItemId(itemId);
                itemLevel.setOperateAccount(operateAccount);
                updateNum = itemLevelService.updateStatusByItemId(itemLevel);

                if(updateNum <= 0){
                    LOGGER.warn(">>ItemServiceImpl.publish item is null.. itemId: {}", itemId);
                    throw new BusinessException("ItemServiceImpl.publisthrow exception");
                }
            }

            //修改项目状态
            int itemUpdateNum = this.update(params);
            if(itemUpdateNum <= 0){
                LOGGER.warn(">>ItemServiceImpl.publish item is null.. itemId: {}", itemId);
                throw new BusinessException("ItemServiceImpl.publisthrow exception");
            }

            //插入记录
            ItemOperateRecord itemOperateRecord = new ItemOperateRecord();
            itemOperateRecord.setItemId(itemId);
            itemOperateRecord.setOperateType(Constants.RECORD_PUBLISH);
            itemOperateRecord.setOperateStatus(Constants.STATUS_YES);
            itemOperateRecord.setOperateRemark("");
            itemOperateRecord.setOperateTime(new Date());
            itemOperateRecord.setOperateAccount(operateAccount);

            int insertNum = itemOperateRecordService.insert(itemOperateRecord);
            if(insertNum != 1){
                LOGGER.warn(">>ItemOperateRecordServiceImpl.publish insert operate record fail... return insertNum is {}", insertNum);
                throw new BusinessException("ItemOperateRecordServiceImpl.publish insert operate record fail...");
            }

        }catch (Exception e){
            throw new BusinessException("ItemOperateRecordServiceImpl.review throw exception:",e);
        }
    }


}

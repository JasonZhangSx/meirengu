package com.meirengu.cf.service;
import com.meirengu.cf.model.Item;
import com.meirengu.service.BaseService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Item服务接口 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface ItemService extends BaseService<Item>{

    int updateStatus(Item item);

    int changeAmount(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum, BigDecimal appointAmount, BigDecimal completedAmount);

    int levelRollback(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum, BigDecimal completedAmount);

    Map<String, Object> moreDetail(int id);

    /**
     * 支付成功修改项目的已筹总金额
     * @param itemId
     * @param amount
     * @return
     */
    void updateItemCompleteAmount(Integer itemId, BigDecimal amount);

    /**
     * 初审操作
     * @return
     */
    void verify(int itemId, int operateStatus, String operateRemark, String operateAccount);

    /**
     * 设置合作
     * @return
     */
    void setCooperate(int itemId, int operateStatus, String operateRemark, String operateAccount);

    /**
     * 复审操作
     * @return
     */
    void review(int itemId, int operateStatus, String operateRemark, String operateAccount);

    /**
     * 发布
     * @return
     */
    boolean publish(int itemId, Date appointDate, int type, String operateAccount);
}

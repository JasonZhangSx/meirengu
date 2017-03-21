package com.meirengu.cf.service;
import com.meirengu.cf.model.Item;
import com.meirengu.service.BaseService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Item服务接口 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface ItemService extends BaseService<Item>{

    int updateStatus(Item item);

    int changeAmount(Integer itemId, BigDecimal levelAmount, Integer levelId, Integer itemNum, BigDecimal appointAmount, BigDecimal completedAmount);

    Map<String, Object> moreDetail(int id);
}

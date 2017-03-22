package com.meirengu.cf.service;
import com.meirengu.cf.model.ItemInterested;
import com.meirengu.service.BaseService;

/**
 * ItemInterested服务接口 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
public interface ItemInterestedService extends BaseService<ItemInterested>{

    int cancle(ItemInterested itemInterested);

    boolean isBeInterested(Integer itemId, Integer userId);
}

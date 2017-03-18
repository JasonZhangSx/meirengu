package com.meirengu.cf.service.impl;
import com.meirengu.cf.common.Constants;
import com.meirengu.cf.dao.ItemInterestedDao;
import com.meirengu.cf.model.ItemInterested;
import com.meirengu.cf.service.ItemInterestedService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ItemInterested服务实现层 
 * @author 建新
 * @create Wed Mar 15 10:41:25 CST 2017
 */
@Service
public class ItemInterestedServiceImpl extends BaseServiceImpl<ItemInterested> implements ItemInterestedService{

    @Autowired
    ItemInterestedDao itemInterestedDao;

    @Override
    public int cancle(ItemInterested itemInterested) {
        return itemInterestedDao.cancle(itemInterested);
    }

    @Override
    public boolean isBeInterested(Integer itemId, Integer userId) {
        ItemInterested params = new ItemInterested();
        params.setUserId(userId);
        params.setItemId(itemId);
        ItemInterested itemInterested = itemInterestedDao.detailByUserAndItem(params);
        if(itemInterested == null){
            return false;
        }else {
            if(Constants.BE_INTERESTED == itemInterested.getStatus()){
                return true;
            }else {
                return false;
            }
        }
    }
}

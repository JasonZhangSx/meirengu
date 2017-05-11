package com.meirengu.cf.service;
import com.meirengu.cf.model.ItemCooperation;
import com.meirengu.service.BaseService;

/**
 * ItemCooperation服务接口 
 * @author 建新
 * @create Mon Apr 10 16:18:41 CST 2017
 */
public interface ItemCooperationService extends BaseService<ItemCooperation>{

    /**
     * 通过项目id获取合作信息
     * @param itemId
     * @return
     */
    ItemCooperation getByItemId(int itemId);

    /**
     * 设置合作
     * @param cooperation
     * @return
     */
    void setCooperate(ItemCooperation cooperation);
}

package com.meirengu.trade.service;
import com.meirengu.model.Page;
import com.meirengu.trade.model.Order;
import com.meirengu.service.BaseService;

import java.io.IOException;
import java.util.Map;

/**
 * Order服务接口 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface OrderService extends BaseService<Order>{

    /**
     *获取后台订单列表
     * @param page
     * @param map
     * @return
     */
    public Page getSystemPage(Page page, Map map)  throws IOException;
}

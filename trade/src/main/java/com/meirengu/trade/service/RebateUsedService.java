package com.meirengu.trade.service;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.model.RebateUsed;
import com.meirengu.service.BaseService;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * RebateUsed服务接口 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateUsedService extends BaseService<RebateUsed>{

    /**
     * 优惠券的使用
     * @param rebateReceiveId
     * @param orderSn
     * @return
     */
    void rebateUse(int rebateReceiveId, String orderSn)  throws OrderException;

    /**
     * 根据条件进行分页
     * @param page
     * @param map
     * @return
     */
    Page getVerifyInfoByPage(Page page, Map map);
    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    Integer getVerifyInfoCount(Map map);
//    /**
//     * 查询符合条件的记录数量
//     * @param map
//     * @return
//     */
//    Integer getUsedCount(Map map);
}

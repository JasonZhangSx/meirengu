package com.meirengu.trade.service;
import com.meirengu.model.Result;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.service.BaseService;

/**
 * RebateBatch服务接口 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
public interface RebateBatchService extends BaseService<RebateBatch>{

    /**
     * 新增抵扣券批次
     * @param rebateBatch
     * @return
     */
    Result insertRebateBatch(RebateBatch rebateBatch);
}

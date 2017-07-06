package com.meirengu.commons.authority.service;
import com.meirengu.commons.authority.model.LogOperation;
import com.meirengu.service.BaseService;

import java.io.IOException;

/**
 * OrderCandidate服务接口 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface LogOperationService extends BaseService<LogOperation>{

    /**
     * 操作记录新增
     * @param businessName    业务模块名称
     * @param operationType   操作类型
     * @param primaryKey      业务主键
     * @param userId
     * @param userName
     * @param detailStr
     */
    public void addLogOperation(String businessName, Integer operationType, String primaryKey, Integer userId,
                                String userName, String detailStr) ;

}

package com.meirengu.commons.authority.service.impl;
import com.meirengu.commons.authority.dao.LogOperationDao;
import com.meirengu.commons.authority.dao.LogOperationDetailDao;
import com.meirengu.commons.authority.model.LogOperation;
import com.meirengu.commons.authority.model.LogOperationDetail;
import com.meirengu.commons.authority.service.LogOperationService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderCandidate服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class LogOperationServiceImpl extends BaseServiceImpl<LogOperation> implements LogOperationService{

    private static final Logger logger = LoggerFactory.getLogger(LogOperationServiceImpl.class);

    @Autowired
    private LogOperationDao logOperationDao;

    @Autowired
    private LogOperationDetailDao logOperationDetailDao;

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
                                String userName, String detailStr) {
        LogOperation logOperation = new LogOperation();
        logOperation.setBusinessName(businessName);
        logOperation.setOperationType(operationType);
        logOperation.setPrimaryKey(primaryKey);
        logOperation.setUserId(userId);
        logOperation.setUserName(userName);
        int i = logOperationDao.insert(logOperation);

        // 内容以 column_name|column_old_value|column_new_value形式组织，多个字段已逗号相隔
        List<LogOperationDetail> detailList = null;
        if (StringUtils.isNotBlank(detailStr)) {
            detailList = new ArrayList<LogOperationDetail>();
            LogOperationDetail logOperationDetail = null;
            String[] detailArr = detailStr.split(",");
            for (String detailString : detailArr) {
                if (StringUtils.isNotBlank(detailString)) {
                    String[] strArr = detailString.split("\\|");
                    logOperationDetail = new LogOperationDetail();
                    logOperationDetail.setLogOperationId(logOperation.getLogId());
                    logOperationDetail.setColumnName(strArr[0]);
                    logOperationDetail.setColumnOldValue(strArr[1]);
                    logOperationDetail.setColumnNewValue(strArr[2]);
                    detailList.add(logOperationDetail);
                }
            }
            logOperationDetailDao.batchAdd(detailList);
        }

    }

}

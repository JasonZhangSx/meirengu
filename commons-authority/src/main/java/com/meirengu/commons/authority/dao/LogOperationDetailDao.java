package com.meirengu.commons.authority.dao;

import com.meirengu.commons.authority.model.LogOperationDetail;
import com.meirengu.dao.BaseDao;

import java.util.List;

/**
 * OrderCandidateDao 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
public interface LogOperationDetailDao extends BaseDao<LogOperationDetail>{

    public int batchAdd(List<LogOperationDetail> detailList);
}

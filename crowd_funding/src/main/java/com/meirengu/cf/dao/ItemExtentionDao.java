package com.meirengu.cf.dao;
import com.meirengu.cf.model.ItemExtention;
import com.meirengu.dao.BaseDao;

import java.util.Map;

/**
 * ItemExtentionDao 
 * @author 建新
 * @create Tue May 23 14:41:52 CST 2017
 */
public interface ItemExtentionDao extends BaseDao<ItemExtention>{

    /**
     * 通过投资人id获取项目id
     * @param leadInvestorId
     * @return
     */
    String getIdsByInvestorId(int leadInvestorId);
}

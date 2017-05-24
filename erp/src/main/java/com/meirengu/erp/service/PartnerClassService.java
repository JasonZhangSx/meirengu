package com.meirengu.erp.service;

import com.meirengu.erp.model.LeadInvestor;
import com.meirengu.erp.model.Partner;
import com.meirengu.erp.model.PartnerClass;

import java.util.Map;

/**
 * 行业分类service
 * @author 建新
 * @create 2017-05-19 11:31
 */
public interface PartnerClassService {

    Object query(int page, int perPage, boolean isPage);

    Map<String, Object> detail(int id);

    Map<String, Object> add(PartnerClass partnerClass);

    Map<String, Object> update(PartnerClass partnerClass);

    Map<String, Object> delete(int id);
}

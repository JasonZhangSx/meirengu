package com.meirengu.erp.service;

import com.meirengu.erp.model.Partner;
import com.meirengu.utils.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-29 21:33
 */
public interface PartnerService {

    Object query(int page, int perPage, boolean isPage, String typeId);

    Map<String, Object> detail(int id);

    Map<String, Object> add(Partner partner);

    Map<String, Object> update(Partner partner);

    Map<String, Object> delete(int id);

}

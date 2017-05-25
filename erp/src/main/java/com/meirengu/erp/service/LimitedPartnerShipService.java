package com.meirengu.erp.service;

import com.meirengu.erp.model.LimitedPartnership;

import java.util.Map;

/**
 * 领投人service
 *
 * @author 建新
 * @create 2017-05-19 11:31
 */
public interface LimitedPartnerShipService {

    Map<String, Object> query(int page, int perPage, boolean isPage);

    Map<String, Object> detail(int id);

    Map<String, Object> add(LimitedPartnership partnership);

    Map<String, Object> update(LimitedPartnership partnership);

    Map<String, Object> delete(int id);
}
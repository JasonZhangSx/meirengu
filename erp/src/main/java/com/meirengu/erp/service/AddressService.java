package com.meirengu.erp.service;

import java.util.List;

/**
 * 获取省市县服务
 * @author 建新
 * @create 2017-05-02 16:56
 */
public interface AddressService {

    List getProvinceList();

    List getCityList(Integer pid);

    List getAreaList(Integer cid);
}

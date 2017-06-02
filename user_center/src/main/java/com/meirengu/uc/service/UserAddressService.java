package com.meirengu.uc.service;

import com.meirengu.service.BaseService;
import com.meirengu.uc.model.UserAddress;
import java.util.List;
import java.util.Map;

/**
 * 地址增删改查
 * Created by huoyan403 on 3/14/2017.
 */
public interface UserAddressService extends BaseService<UserAddress> {
    /**
     * 插入用户地址  不可缺少数据
     */
    int insert(UserAddress userAddress);

    /**
     * 根据用户信息获取用户地址
     */
    UserAddress selectByUserAddress(UserAddress userAddress);

    /**
     * 修改用户地址
     */
    int updateByAdressId(UserAddress userAddress);

    /**
     * 根据手机号查询默认地址
     */
    UserAddress selectDefaultAddressByUserId(Integer userId);

    List<Map<String, Object>> selectByAddIdArray(String addressIds);
}

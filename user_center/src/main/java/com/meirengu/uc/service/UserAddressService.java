package com.meirengu.uc.service;

import com.meirengu.service.BaseService;
import com.meirengu.uc.model.UserAddress;

import java.util.List;

/**
 * 地址增删改查
 * Created by huoyan403 on 3/14/2017.
 */
public interface UserAddressService extends BaseService<UserAddress> {
    /**
     * 插入用户地址  不可缺少数据
     * @param userAddress
     * @return
     */
    int insert(UserAddress userAddress);

    /**
     * 根据用户信息获取用户地址
     * @param userAddress
     * @return
     */
    UserAddress selectByUserAddress(UserAddress userAddress);

    /**
     * 修改用户地址
     * @param userAddress
     * @return
     */
    int updateByAdressId(UserAddress userAddress);

    /**
     * 根据手机号查询默认地址
     * @param userId
     * @return
     */
    UserAddress selectDefaultAddressByUserId(Integer userId);

    List<UserAddress> selectByAddIdArray(String addressIds);
}

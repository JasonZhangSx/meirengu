package com.meirengu.uc.dao;

import com.meirengu.dao.BaseDao;
import com.meirengu.uc.model.UserAddress;

import java.util.List;
import java.util.Map;

public interface UserAddressDao  extends BaseDao<UserAddress> {
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
     *清空用户默认地址
     * @param userId
     */
    void updateClearDefaultAddress(Integer userId);

    List<Map<String, Object>> selectByAddIdList(List<String> list);
}
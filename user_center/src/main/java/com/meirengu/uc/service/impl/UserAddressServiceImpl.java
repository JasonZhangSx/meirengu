package com.meirengu.uc.service.impl;

import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.UserAddressDao;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huoyan403 on 3/14/2017.
 */
@Service
public class UserAddressServiceImpl  extends BaseServiceImpl<UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressDao userAddressDao;

    @Override
    public int insert(UserAddress userAddress) {

        if(userAddress.getDefault()){
            userAddressDao.updateClearDefaultAddress(userAddress.getUserId());
        }else{

        }
        return userAddressDao.insert(userAddress);
    }

    @Override
    public UserAddress selectByUserAddress(UserAddress userAddress) {
        return userAddressDao.selectByUserAddress(userAddress);
    }

    @Override
    public int updateByAdressId(UserAddress userAddress) {

        if(userAddress.getDefault()){
            userAddressDao.updateClearDefaultAddress(userAddress.getUserId());
        }else{

        }
        return userAddressDao.updateByAdressId(userAddress);
    }

    @Override
    public UserAddress selectDefaultAddressByPhone(Integer userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setDefault(true);
        userAddress.setDelFlag(0);
        return userAddressDao.selectByUserAddress(userAddress);
    }

}

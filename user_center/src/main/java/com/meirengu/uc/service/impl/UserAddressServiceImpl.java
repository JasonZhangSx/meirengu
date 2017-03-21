package com.meirengu.uc.service.impl;

import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.dao.UserAddressDao;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.po.AddressNamePO;
import com.meirengu.uc.po.AddressPO;
import com.meirengu.uc.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huoyan403 on 3/14/2017.
 */
@Service
public class UserAddressServiceImpl  extends BaseServiceImpl<UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    AreasMapper areasMapper;
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

        if(userAddress.getDefault() !=null){
            if(userAddress.getDefault()){
                userAddressDao.updateClearDefaultAddress(userAddress.getUserId());
            }else{

            }
        }
        return userAddressDao.updateByAdressId(userAddress);
    }

    @Override
    public UserAddress selectDefaultAddressByUserId(Integer userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setDefault(true);
        userAddress.setDelFlag(0);
        return userAddressDao.selectByUserAddress(userAddress);
    }

    @Override
    public List<AddressNamePO> selectByAddIdArray(String addressIds) {

        List<String> list = new ArrayList<>();
        String[] arr = addressIds.split(",");
        for(String arr1 :arr){
            list.add(arr1);
        }
        List<AddressNamePO> addressNamePOs = new ArrayList<>();
        List<UserAddress> addressList = userAddressDao.selectByAddIdList(list);

        for(UserAddress address:addressList){
            AddressNamePO addressNamePO = new AddressNamePO();
            addressNamePO.setAddressId(address.getAddressId());
            addressNamePO.setUserAddress(address.getUserAddress());
            AddressPO addressPO = new AddressPO();
            Area area = areasMapper.getArea(address.getAreaId());
            if(area!=null && area.getAreaDeep() == 3){
                addressPO.setAreaId(address.getAreaId());
                addressPO.setArea(area.getAreaName());

                Area city = areasMapper.getArea(area.getAreaParentId());
                addressPO.setCityId(city.getAreaId());
                addressPO.setCity(city.getAreaName());

                Area province = areasMapper.getArea(city.getAreaParentId());
                addressPO.setProvinceId(city.getAreaParentId());
                addressPO.setProvince(province.getAreaName());
            }else if(area!=null && area.getAreaDeep()== 2){

                addressPO.setCityId(address.getAreaId());
                addressPO.setCity(area.getAreaName());

                Area province = areasMapper.getArea(area.getAreaParentId());
                addressPO.setProvinceId(area.getAreaParentId());
                addressPO.setProvince(province.getAreaName());
            }else if(area!=null && area.getAreaDeep()==1){
                addressPO.setProvinceId(address.getAreaId());
                addressPO.setProvince(area.getAreaName());
            }
            addressNamePO.setAddressPO(addressPO);
            addressNamePOs.add(addressNamePO);
        }

        return addressNamePOs;
    }

}

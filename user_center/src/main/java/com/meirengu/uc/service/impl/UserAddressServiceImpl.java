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

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> selectByAddIdArray(String addressIds) {
        List<String> list = new ArrayList<>();
        String[] arr = addressIds.split(",");
        for(String arr1 :arr){
            list.add(arr1);
        }
        List<Map<String, Object>> addressList = userAddressDao.selectByAddIdList(list);

        if (addressList != null && addressList.size() > 0) {
            for(Map<String, Object> addressMap:addressList){
                Area area = areasMapper.getArea((int)addressMap.get("areaId"));
                if(area!=null && area.getAreaDeep() == 3){
                    addressMap.put("area", area.getAreaName());

                    Area city = areasMapper.getArea(area.getAreaParentId());
                    addressMap.put("city", city.getAreaName());

                    Area province = areasMapper.getArea(city.getAreaParentId());
                    addressMap.put("province", province.getAreaName());
                }else if(area!=null && area.getAreaDeep()== 2){
                    addressMap.put("city", area.getAreaName());

                    Area province = areasMapper.getArea(area.getAreaParentId());
                    addressMap.put("province", province.getAreaName());
                }else if(area!=null && area.getAreaDeep()==1){
                    addressMap.put("province", area.getAreaName());
                }
            }
        }
        return addressList;

    }

}

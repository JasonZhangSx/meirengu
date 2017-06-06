package com.meirengu.uc.service.impl;

import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.AreasDao;
import com.meirengu.uc.dao.UserAddressDao;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.service.UserAddressService;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    AreasDao areasDao;
    @Override
    @Transactional
    public int insert(UserAddress userAddress) {
        //查询是否有地址 没有强制为默认地址
        UserAddress address = new UserAddress();
        address.setUserId(userAddress.getUserId());
        address.setIsDefault(1);
        address = userAddressDao.selectByUserAddress(address);
        if(address==null || StringUtil.isEmpty(address.getAddressId())){
            userAddress.setIsDefault(1);
        }
        //如果新增默认地址要清空数据库 原有默认地址
        if(1==(userAddress.getIsDefault())){
            userAddressDao.updateClearDefaultAddress(userAddress.getUserId());
        }
        return userAddressDao.insert(userAddress);
    }

    @Override
    public UserAddress selectByUserAddress(UserAddress userAddress) {
        return userAddressDao.selectByUserAddress(userAddress);
    }

    @Override
    @Transactional
    public int updateByAdressId(UserAddress userAddress) {

        if(!StringUtil.isEmpty(userAddress.getIsDefault())){
            userAddressDao.updateClearDefaultAddress(userAddress.getUserId());
        }
        return userAddressDao.updateByAdressId(userAddress);
    }

    @Override
    public UserAddress selectDefaultAddressByUserId(Integer userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setIsDefault(1);
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
                Area area = areasDao.getArea((int)addressMap.get("areaId"));
                if(area!=null && area.getAreaDeep() == 3){
                    addressMap.put("area", area.getAreaName());

                    Area city = areasDao.getArea(area.getAreaParentId());
                    addressMap.put("city", city.getAreaName());

                    Area province = areasDao.getArea(city.getAreaParentId());
                    addressMap.put("province", province.getAreaName());
                }else if(area!=null && area.getAreaDeep()== 2){
                    addressMap.put("city", area.getAreaName());

                    Area province = areasDao.getArea(area.getAreaParentId());
                    addressMap.put("province", province.getAreaName());
                }else if(area!=null && area.getAreaDeep()==1){
                    addressMap.put("province", area.getAreaName());
                }
            }
        }
        return addressList;

    }

}

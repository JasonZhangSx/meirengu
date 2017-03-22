package com.meirengu.uc.service;

import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.po.AddressPO;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AddressService {

    @Autowired
    AreasMapper areasMapper;
    @Autowired
    UserDao userDao;

    // 查询出所有的省份
    @Transactional(readOnly = true)
    public List<Area> showProvinceList() {
        return areasMapper.showProvinceList();
    }
    // 根据省id来查询所对应的城市名称
    @Transactional(readOnly = true)
    public List<Area> showCityListByPid(int pid) {
        return areasMapper.showCityListByPid(pid);
    }

    // 根据city的id来查询所有的区、县
    @Transactional(readOnly = true)
    public List<Area> showAreaListBycid(int cid) {
        return areasMapper.showAreaListBycid(cid);
    }

    public AddressPO showAddress(Integer area_id) {

        if(!StringUtil.isEmpty(area_id)){
            AddressPO addressPO = new AddressPO();

            Area area = areasMapper.getArea(area_id);
            if(area!=null && area.getAreaDeep() == 3){
                addressPO.setAreaId(area_id);
                addressPO.setArea(area.getAreaName());

                Area city = areasMapper.getArea(area.getAreaParentId());
                addressPO.setCityId(city.getAreaId());
                addressPO.setCity(city.getAreaName());

                Area province = areasMapper.getArea(city.getAreaParentId());
                addressPO.setProvinceId(city.getAreaParentId());
                addressPO.setProvince(province.getAreaName());
                return  addressPO;
            }else if(area!=null && area.getAreaDeep()== 2){

                addressPO.setCityId(area_id);
                addressPO.setCity(area.getAreaName());

                Area province = areasMapper.getArea(area.getAreaParentId());
                addressPO.setProvinceId(area.getAreaParentId());
                addressPO.setProvince(province.getAreaName());
                return  addressPO;
            }else if(area!=null && area.getAreaDeep()==1){
                addressPO.setProvinceId(area_id);
                addressPO.setProvince(area.getAreaName());
                return  addressPO;
            }
        }
        return  null;
    }
}

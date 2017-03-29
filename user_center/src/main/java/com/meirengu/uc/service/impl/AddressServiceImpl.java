package com.meirengu.uc.service.impl;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.po.AddressPO;
import com.meirengu.uc.service.AddressService;
import com.meirengu.uc.thread.AddressToRedisThread;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AreasMapper areasMapper;
    @Autowired
    UserDao userDao;
    @Autowired
    private RedisClient redisClient;

    // 查询出所有的省份
    @Transactional(readOnly = true)
    public List<Area> showProvinceList() {
        List<Area> provinceList = new ArrayList<>();
        if(redisClient.existsObject("provinceList")){
            provinceList = (List<Area>)redisClient.getList("provinceList");
        }else{
            provinceList = areasMapper.showProvinceList();
            redisClient.setList("provinceList",provinceList,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        return provinceList;
    }
    // 根据省id来查询所对应的城市名称
    @Transactional(readOnly = true)
    public List<Area> showCityListByPid(int pid) {

        List<Area> cityList = new ArrayList<>();
        if(redisClient.existsObject("cityList_"+pid)){
            cityList = (List<Area>)redisClient.getList("cityList_"+pid);
        }else{
            cityList = areasMapper.showCityListByPid(pid);
            redisClient.setList("cityList_"+pid,cityList,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        return cityList;
    }

    // 根据city的id来查询所有的区、县
    @Transactional(readOnly = true)
    public List<Area> showAreaListBycid(int cid) {

        List<Area> areaList = new ArrayList<>();
        if(redisClient.existsObject("areaList_"+cid)){
            areaList = (List<Area>)redisClient.getList("areaList_"+cid);
        }else{
            areaList = areasMapper.showAreaListBycid(cid);
            redisClient.setList("areaList_"+cid,areaList,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        return areaList;
    }

    public AddressPO showAddress(Integer area_id) {

        if(redisClient.existsObject("area_"+area_id)){
            Area area = JacksonUtil.readValue((String) redisClient.getObject("area_"+area_id),Area.class);
            AddressPO addressPO = new AddressPO();
            if(area.getAreaDeep()==3){
                addressPO.setAreaId(area_id);
                addressPO.setArea(area.getAreaName());

                Area city = JacksonUtil.readValue((String) redisClient.getObject("area_"+area.getAreaParentId()),Area.class);
                addressPO.setCity(city.getAreaName());
                addressPO.setCityId(city.getAreaId());

                Area province = JacksonUtil.readValue((String) redisClient.getObject("area_"+city.getAreaParentId()),Area.class);
                addressPO.setProvince(province.getAreaName());
                addressPO.setProvinceId(province.getAreaId());
                return  addressPO;
            }else if(area.getAreaDeep()==2){
                addressPO.setCityId(area_id);
                addressPO.setCity(area.getAreaName());

                Area province = JacksonUtil.readValue((String) redisClient.getObject("area_"+area.getAreaParentId()),Area.class);
                addressPO.setProvince(province.getAreaName());
                addressPO.setProvinceId(province.getAreaId());
                return  addressPO;
            }else if(area.getAreaDeep()==1){
                addressPO.setProvince(area.getAreaName());
                addressPO.setProvinceId(area.getAreaId());
                return  addressPO;
            }
        }else{
            //异步存储redis
            AddressToRedisThread addressToRedisThread = new AddressToRedisThread();
            addressToRedisThread.setAreasMapper(areasMapper);
            addressToRedisThread.setRedisClient(redisClient);
            addressToRedisThread.run();

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
        }
        return  null;
    }
}

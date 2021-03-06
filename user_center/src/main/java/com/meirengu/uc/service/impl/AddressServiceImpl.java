package com.meirengu.uc.service.impl;

import com.meirengu.common.RedisClient;
import com.meirengu.uc.dao.AreasDao;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Area;
import com.meirengu.uc.vo.request.AddressVO;
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
    AreasDao areasDao;
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
            provinceList = areasDao.showProvinceList();
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
            cityList = areasDao.showCityListByPid(pid);
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
            areaList = areasDao.showAreaListBycid(cid);
            redisClient.setList("areaList_"+cid,areaList,Integer.parseInt(ConfigUtil.getConfig("ADDRESS_TIME_REDIS")));
        }
        return areaList;
    }

    public AddressVO showAddress(Integer area_id) {

        Area areas = JacksonUtil.readValue((String)redisClient.get("area_"+area_id),Area.class);
        if(areas != null){
            AddressVO addressVO = new AddressVO();
            if(areas.getAreaDeep()==3){
                addressVO.setAreaId(area_id);
                addressVO.setArea(areas.getAreaName());

                Area city = JacksonUtil.readValue((String) redisClient.get("area_"+areas.getAreaParentId()),Area.class);
                addressVO.setCity(city.getAreaName());
                addressVO.setCityId(city.getAreaId());

                Area province = JacksonUtil.readValue((String) redisClient.get("area_"+city.getAreaParentId()),Area.class);
                addressVO.setProvince(province.getAreaName());
                addressVO.setProvinceId(province.getAreaId());
                return addressVO;
            }else if(areas.getAreaDeep()==2){
                addressVO.setCityId(area_id);
                addressVO.setCity(areas.getAreaName());

                Area province = JacksonUtil.readValue((String) redisClient.get("area_"+areas.getAreaParentId()),Area.class);
                addressVO.setProvince(province.getAreaName());
                addressVO.setProvinceId(province.getAreaId());
                return addressVO;
            }else if(areas.getAreaDeep()==1){
                addressVO.setProvince(areas.getAreaName());
                addressVO.setProvinceId(areas.getAreaId());
                return addressVO;
            }
        }else{
            //异步存储redis
            AddressToRedisThread addressToRedisThread = new AddressToRedisThread();
            addressToRedisThread.setAreasDao(areasDao);
            addressToRedisThread.setRedisClient(redisClient);
            addressToRedisThread.run();

            if(!StringUtil.isEmpty(area_id)){
                AddressVO addressVO = new AddressVO();

                Area area = areasDao.getArea(area_id);
                if(area!=null && area.getAreaDeep() == 3){
                    addressVO.setAreaId(area_id);
                    addressVO.setArea(area.getAreaName());

                    Area city = areasDao.getArea(area.getAreaParentId());
                    addressVO.setCityId(city.getAreaId());
                    addressVO.setCity(city.getAreaName());

                    Area province = areasDao.getArea(city.getAreaParentId());
                    addressVO.setProvinceId(city.getAreaParentId());
                    addressVO.setProvince(province.getAreaName());
                    return addressVO;
                }else if(area!=null && area.getAreaDeep()== 2){

                    addressVO.setCityId(area_id);
                    addressVO.setCity(area.getAreaName());

                    Area province = areasDao.getArea(area.getAreaParentId());
                    addressVO.setProvinceId(area.getAreaParentId());
                    addressVO.setProvince(province.getAreaName());
                    return addressVO;
                }else if(area!=null && area.getAreaDeep()==1){
                    addressVO.setProvinceId(area_id);
                    addressVO.setProvince(area.getAreaName());
                    return addressVO;
                }
            }
        }
        return  null;
    }
}

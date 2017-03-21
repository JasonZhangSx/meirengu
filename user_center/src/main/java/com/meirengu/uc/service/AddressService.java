package com.meirengu.uc.service;

import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Area;
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

}

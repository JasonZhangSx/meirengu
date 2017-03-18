package com.meirengu.uc.service;

import com.meirengu.uc.dao.AreasMapper;
import com.meirengu.uc.dao.CitysMapper;
import com.meirengu.uc.dao.ProvincesMapper;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Areas;
import com.meirengu.uc.model.Citys;
import com.meirengu.uc.model.Provinces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AddressService {
    @Autowired
    ProvincesMapper dao;

    @Autowired
    CitysMapper dao1;

    @Autowired
    AreasMapper dao2;
    @Autowired
    UserDao userDao;

    // 查询出所有的省份
    @Transactional(readOnly = true)
    public List<Provinces> showProvinceList() {
        return dao.showProvinceList();
    }
    // 根据省id来查询所对应的城市名称
    @Transactional(readOnly = true)
    public List<Citys> showCityListByPid(int pid) {
        return dao1.showCityListByPid(pid);
    }

    // 根据city的id来查询所有的区、县
    @Transactional(readOnly = true)
    public List<Areas> showAreaListBycid(int cid) {
        return dao2.showAreaListBycid(cid);
    }

}

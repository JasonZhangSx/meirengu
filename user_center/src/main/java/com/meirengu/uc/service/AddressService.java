package com.meirengu.uc.service;

import com.meirengu.uc.model.Area;
import com.meirengu.uc.vo.request.AddressVO;

import java.util.List;

/**
 * Created by huoyan403 on 3/22/2017.
 */
public interface AddressService {

    List<Area> showProvinceList();

    List<Area> showCityListByPid(int pid);

    List<Area> showAreaListBycid(int cid);

    AddressVO showAddress(Integer area_id);
}

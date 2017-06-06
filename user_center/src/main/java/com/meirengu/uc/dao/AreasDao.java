package com.meirengu.uc.dao;


import com.meirengu.uc.model.Area;

import java.util.List;

/**
 * 区、县接口
 * 
 * @author Zhang wenmeng
 * 
 */
public interface AreasDao {

	List<Area> showProvinceList();

	List<Area> showCityListByPid(int pid);

	// 根据city的id来查询所有的区、县
	List<Area> showAreaListBycid(Integer cid);

	Area getArea(Integer areaId);

	List<Area> getAreaData();
}
package com.meirengu.uc.dao;


import com.meirengu.uc.model.Citys;

import java.util.List;

/**
 * 城市接口
 * 
 * @author Zhang wenmeng
 * 
 */
public interface CitysMapper {
	// 根据省id来查询对应的城市
	List<Citys> showCityListByPid(Integer id);
}
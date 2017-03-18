package com.meirengu.uc.dao;


import com.meirengu.uc.model.Provinces;

import java.util.List;

/**
 * 省接口

 * @author Zhang wenmeng
 * 
 */
public interface ProvincesMapper {

	// 查询出所有的省id
	List<Provinces> showProvinceList();
}
package com.meirengu.uc.dao;


import com.meirengu.uc.model.Areas;

import java.util.List;

/**
 * 区、县接口
 * 
 * @author Zhang wenmeng
 * 
 */
public interface AreasMapper {
	// 根据city的id来查询所有的区、县
	List<Areas> showAreaListBycid(Integer cid);

}
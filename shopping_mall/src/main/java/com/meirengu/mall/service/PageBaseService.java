package com.meirengu.mall.service;


import com.meirengu.mall.model.Page;

import java.util.List;
import java.util.Map;

/**
 * 分页基类服务
 * @author 建新
 * @create 2017-01-10 17:15
 */
public interface PageBaseService<E> {

	/**
	 * 分页方法，实现了此方法，并调用getListByPage(Page<E> page, E entity, PageDao)即可
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<E> getPageList(Page<E> page, Map map);

}

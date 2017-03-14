package com.meirengu.uc.service;


import com.meirengu.model.Page;

import java.util.List;
import java.util.Map;

public interface PageBaseService<E> {

	/**
	 * 分页方法，实现了此方法，并调用getListByPage(Page<E> page, E entity, PageDao)即可
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<E> getPageList(Page<E> page, Map map);

	/**
	 * 不分页获取列表
	 * @param map
     * @return
     */
	public List<Map<String, Object>> getList(Map map);
}

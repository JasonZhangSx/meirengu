package com.meirengu.news.service;


import com.meirengu.news.dao.PageDao;
import com.meirengu.news.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/30.
 * @param <E>
 */
public interface PageService<E> {

	/**
	 * 实现分页获取list
	 * @param page
	 * @param map 
	 * @return
	 */
	public Page<E> getListByPage(Page<E> page, Map map, PageDao<E> pageDao);

	public List<Map<String, Object>> getList(Map map, PageDao<E> pageDao);
}

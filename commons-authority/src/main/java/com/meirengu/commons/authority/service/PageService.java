package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.dao.AccessPageDao;
import com.meirengu.commons.authority.model.Page;

public interface PageService<E> {

	/**
	 * 实现分页获取list
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<E> getListByPage(Page<E> page, E entity, AccessPageDao<E> pageDao);
	
}

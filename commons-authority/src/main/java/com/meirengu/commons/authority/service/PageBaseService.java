package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Page;

public interface PageBaseService<E> {

	/**
	 * 分页方法，实现了此方法，并调用getListByPage(Page<E> page, E entity, PageDao)即可
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<E> getPageList(Page<E> page, E entity);
}

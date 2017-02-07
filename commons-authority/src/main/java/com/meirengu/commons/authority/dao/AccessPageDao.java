package com.meirengu.commons.authority.dao;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AccessPageDao<E> {

	/**
	 * 根据条件进行分页
	 * @param rowBounds
	 * @return
	 */
	public List<E> getByPage(E entity, RowBounds rowBounds);
	/**
	 * 根据条件获取总数
	 * @param entity
	 * @return
	 */
	public Integer getTotalCount(E entity);
}

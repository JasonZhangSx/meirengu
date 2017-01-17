package com.meirengu.mall.dao;

import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * controller基类
 *
 * @author 建新
 * @param <E> 要进行分页的实体对象
 * @create 2017-01-10 19:35
 */
public interface PageDao<E> {

	/**
	 * 根据条件进行分页
	 * @param map
	 * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> getByPage(Map map, RowBounds rowBounds);

	/**
	 * 根据条件获取总条数
	 * @param map
	 * @return
     */
	public Integer getTotalCount(Map map);

}

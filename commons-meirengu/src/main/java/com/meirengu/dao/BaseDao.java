package com.meirengu.dao;

import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-10 18:36
 */
public interface BaseDao<E> {

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
    public Integer getCount(Map map);

    /**
     * 新增实体
     * @param e
     * @return
     */
    int insert(E e);

    /**
     * 删除实体
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 修改实体
     * @param e
     * @return
     */
    int update(E e);

    /**
     * 获取详情实体
     * @param id
     * @return
     */
    int detail(int id);
}

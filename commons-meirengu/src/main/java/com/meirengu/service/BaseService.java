package com.meirengu.service;

import com.meirengu.model.Page;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-10 18:23
 */
public interface BaseService<E> {

    /**
     * 分页方法，实现了此方法，并调用getListByPage(Page<E> page, E entity, PageDao)即可
     * @param page
     * @param map
     * @return
     */
    Page<E> getListByPage(Page<E> page, Map map);

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
     * @param e
     * @return
     */
    int detail(int id);
}

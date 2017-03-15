package com.meirengu.service.impl;

import com.alibaba.fastjson.JSON;
import com.meirengu.dao.BaseDao;
import com.meirengu.model.Page;
import com.meirengu.service.BaseService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 基础service
 * @author 建新
 * @create 2017-03-10 18:24
 */
public class BaseServiceImpl<E> implements BaseService<E>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseDao<E> baseDao;

    @Override
    public Page<E> getListByPage(Page<E> page, Map map) {
        int startPos = page.getStartPos();
        int pageSize = page.getPageSize();
        RowBounds rowBounds = new RowBounds(startPos, pageSize);
        List<Map<String, Object>> aList = baseDao.getByPage(map, rowBounds);
        int totalCount = baseDao.getCount(map);
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        LOGGER.info(" page params is "+ JSON.toJSON(map));
        return page;
    }

    @Override
    public int insert(E e) {
        return baseDao.insert(e);
    }

    @Override
    public int delete(int id) {
        return baseDao.delete(id);
    }

    @Override
    public int update(E e) {
        return baseDao.update(e);
    }

    @Override
    public E detail(int id) {
        return baseDao.detail(id);
    }
}

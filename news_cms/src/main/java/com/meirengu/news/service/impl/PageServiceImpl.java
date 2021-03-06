package com.meirengu.news.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.meirengu.news.dao.PageDao;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.PageService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by 建新 on 2016/12/30.
 * @param <E>
 */
@Service
public class PageServiceImpl<E> implements PageService<E> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageServiceImpl.class);

	public Page<E> getListByPage(Page<E> page, Map map, PageDao<E> pageDao) {
		int startPos = page.getStartPos();
		int pageSize = page.getPageSize();
		RowBounds rowBounds = new RowBounds(startPos, pageSize);
		List<Map<String, Object>> aList = pageDao.getByPage(map, rowBounds);
		int totalCount = pageDao.getTotalCount(map);
		page.setTotalCount(totalCount);
		page.setList(aList);
		page.init();
		LOGGER.info(" page params is "+ JSON.toJSON(map));
		return page;
	}

	@Override
	public List<Map<String, Object>> getList(Map map, PageDao<E> pageDao) {
		return pageDao.getByPage(map);
	}
}

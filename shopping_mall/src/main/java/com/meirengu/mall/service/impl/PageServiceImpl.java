package com.meirengu.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.meirengu.mall.dao.PageDao;
import com.meirengu.mall.model.Page;
import com.meirengu.mall.service.PageService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/30.
 * @param <E>
 */
@Service
public class PageServiceImpl<E> implements PageService<E> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageServiceImpl.class);

	@Override
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

}

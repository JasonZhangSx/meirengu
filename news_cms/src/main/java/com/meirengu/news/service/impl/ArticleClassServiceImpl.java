package com.meirengu.news.service.impl;

import com.meirengu.news.dao.ArticleClassDao;
import com.meirengu.news.model.ArticleClass;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.ArticleClassService;
import com.meirengu.news.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/27.
 */
@Service
public class ArticleClassServiceImpl implements ArticleClassService{

    @Autowired
    ArticleClassDao articleClassDao;

    @Autowired
    PageService<ArticleClass> pageService;

    @Override
    public Page<ArticleClass> getPageList(Page<ArticleClass> page, Map map) {
        return pageService.getListByPage(page, map, articleClassDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, articleClassDao);
    }

    @Override
    public int insert(ArticleClass ac) {
        return articleClassDao.insert(ac);
    }

    @Override
    public int update(ArticleClass ac) {
        return articleClassDao.update(ac);
    }

    @Override
    public int delete(int id) {
        return articleClassDao.delete(id);
    }

    @Override
    public ArticleClass detail(int id) {
        return articleClassDao.detail(id);
    }

    @Override
    public List<ArticleClass> getByCodeOrName(ArticleClass articleClass) {
        return articleClassDao.getByCodeOrName(articleClass);
    }
}

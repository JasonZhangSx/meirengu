package com.meirengu.news.service.impl;

import com.meirengu.news.dao.ArticleDao;
import com.meirengu.news.model.Article;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.ArticleService;
import com.meirengu.news.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/27.
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    private static Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private PageService<Article> pageService;

    @Override
    public int insert(Article article) {
        return articleDao.insert(article);
    }

    @Override
    public int update(Article article) {
        return articleDao.update(article);
    }

    @Override
    public int delete(int id) {
        return articleDao.delete(id);
    }

    @Override
    public Map<String, Object> detail(int id){
        return articleDao.detail(id);
    }

    @Override
    public boolean publish(int id, int isPublish) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("isPublish", isPublish);
        if(articleDao.publish(params)>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Page<Article> getPageList(Page<Article> page, Map map) {
        return pageService.getListByPage(page, map, articleDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, articleDao);
    }

}

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

import java.sql.SQLException;
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
    public int insert(Article article) throws Exception{
        try{
            return articleDao.insert(article);
        }catch (Exception e){
            throw new Exception("insert article faile:", e);
        }
    }

    @Override
    public int update(Article article) throws Exception {
        try{
            return articleDao.update(article);
        }catch (Exception e){
            throw new Exception("update article faile:", e);
        }
    }

    @Override
    public int delete(int id) throws Exception{
        try{
            return articleDao.delete(id);
        }catch (Exception e){
            throw new Exception("delete article faile:", e);
        }
    }

    @Override
    public Map<String, Object> detail(int id){
        return articleDao.detail(id);
    }

    @Override
    public boolean publish(int id, int isPublish) throws Exception{
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("isPublish", isPublish);
        try{
            if(articleDao.publish(params)>0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            throw new Exception("update article faile", e);
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

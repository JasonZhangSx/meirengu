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
    public int insert(ArticleClass ac) throws Exception{
        try{
            return articleClassDao.insert(ac);
        }catch (Exception e){
            throw new Exception("insert article class faile:", e);
        }
    }

    @Override
    public int update(ArticleClass ac) throws Exception{
        try{
            return articleClassDao.update(ac);
        }catch (Exception e){
            throw new Exception("update article class faile:", e);
        }
    }

    @Override
    public int delete(int id) throws Exception{
        try{
            return articleClassDao.delete(id);
        }catch (Exception e){
            throw new Exception("delete article class faile:", e);
        }
    }

    @Override
    public ArticleClass detail(int id) throws Exception{
        try{
            return articleClassDao.detail(id);
        }catch (Exception e){
            throw new Exception("get article class faile:", e);
        }
    }

    @Override
    public List<ArticleClass> getByCodeOrName(ArticleClass articleClass) throws Exception{
        try{
            return articleClassDao.getByCodeOrName(articleClass);
        }catch (Exception e){
            throw new Exception("get article class detai faile:", e);
        }
    }
}

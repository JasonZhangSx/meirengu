package com.meirengu.news.service;

import com.meirengu.news.model.ArticleClass;

import java.util.List;

/**
 * Created by 建新 on 2016/12/27.
 */
public interface ArticleClassService extends PageBaseService<ArticleClass>{

    public int insert(ArticleClass ac);

    public int update(ArticleClass ac);

    public int delete(int id);

    public ArticleClass detail(int id);

    /**
     * 通过标识码或名称判断是否重复
     * @param articleClass
     * @return
     */
    public List<ArticleClass> getByCodeOrName(ArticleClass articleClass);
}

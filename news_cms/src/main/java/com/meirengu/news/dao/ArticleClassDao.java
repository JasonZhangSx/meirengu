package com.meirengu.news.dao;

import com.meirengu.news.model.Article;
import com.meirengu.news.model.ArticleClass;

import java.util.List;

/**
 * Created by 建新 on 2016/12/27.
 */
public interface ArticleClassDao extends PageDao<ArticleClass> {

    /**
     * 新增文章分类
     * @param ac
     * @return
     */
    public int insert(ArticleClass ac);

    /**
     * 修改文章分类
     * @param ac
     * @return
     */
    public int update(ArticleClass ac);

    /**
     * 根据ID删除文章分类
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * 通过id获取文章分类详情
     * @param id
     * @return
     */
    public ArticleClass detail(int id);

    /**
     * 通过标识码或名称判断是否重复
     * @param articleClass
     * @return
     */
    public List<ArticleClass> getByCodeOrName(ArticleClass articleClass);
}

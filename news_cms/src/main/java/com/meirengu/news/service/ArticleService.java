package com.meirengu.news.service;

import com.meirengu.news.model.Article;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/27.
 */
public interface ArticleService extends PageBaseService<Article>{

    /**
     * 新增文章
     * @param article
     * @return
     */
    public int insert(Article article) throws Exception;

    /**
     * 修改文章
     * @param article
     * @return
     */
    public int update(Article article) throws Exception;

    /**
     * 删除文章
     * @param id
     * @return
     */
    public int delete(int id) throws Exception;

    /**
     * 文章详情
     * @return
     */
    public Map<String, Object> detail(int id) throws Exception;

    /**
     * 发布/取消发布文章
     * @param id
     * @param isPublish
     * @return
     */
    public boolean publish(int id, int isPublish) throws Exception;

}

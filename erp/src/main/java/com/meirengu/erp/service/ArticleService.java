package com.meirengu.erp.service;

import com.meirengu.erp.model.Article;

import java.util.Map;

/**
 * 文章service
 * @author 建新
 * @create 2017-05-19 11:31
 */
public interface ArticleService {

    Map<String, Object> query(int page, int perPage, boolean isPage, Integer flag);

    Map<String, Object> detail(int id);

    Map<String, Object> add(Article article);

    Map<String, Object> update(Article article);

    Map<String, Object> delete(int id);
}

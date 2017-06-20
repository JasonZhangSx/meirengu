package com.meirengu.erp.service;

import com.meirengu.erp.model.ArticleClass;

import java.util.Map;

/**
 * 文章分类service
 * @author 建新
 * @create 2017-05-19 11:31
 */
public interface ArticleClassService {

    Object query(int page, int perPage, boolean isPage, Integer flag);

    Map<String, Object> detail(int id);

    Map<String, Object> add(ArticleClass articleClass);

    Map<String, Object> update(ArticleClass articleClass);

    Map<String, Object> delete(int id);
}

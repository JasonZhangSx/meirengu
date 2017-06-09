package com.meirengu.webview.service;

/**
 * 文章service
 * @author 建新
 * @create 2017-06-07 11:15
 */
public interface ArticleService {

    Object query(int page, int perPage, boolean isPage, int acId);
}

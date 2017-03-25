package com.meirengu.news.service;

import com.meirengu.news.model.Slideshow;

/**
 * 轮播图Service
 * Author: haoyang.Yu
 * Create Date: 2017/3/25 16:09.
 */
public interface SlideshowService {
    /**
     * 查询轮播图
     * @param slideshow
     * @return
     */
    String getSlideshow(Slideshow slideshow);

    /**
     * 新增轮播图
     * @param slideshow
     * @return
     */
    String insertSlideshow(Slideshow slideshow);

    /**
     * 更新轮播图
     * @param slideshow
     * @return
     */
    String updateSlideshow(Slideshow slideshow);
}

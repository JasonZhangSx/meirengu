package com.meirengu.news.dao;


import com.meirengu.news.model.Slideshow;

import java.util.List;

public interface SlideshowDao {
    int insertSlideshow(Slideshow record);

    List<Slideshow> selectSlideShow(Slideshow slideshow);

    int updateSlideshow(Slideshow record);
}
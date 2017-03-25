package com.meirengu.news.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.news.dao.SlideshowDao;
import com.meirengu.news.model.Slideshow;
import com.meirengu.news.service.SlideshowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮播图Impl
 * Author: haoyang.Yu
 * Create Date: 2017/3/25 16:10.
 */
@Service
public class SlideshowServiceImpl extends BaseImpl implements SlideshowService {
    private final static Logger logger = LoggerFactory.getLogger(SlideshowServiceImpl.class);
    @Autowired
    private SlideshowDao slideshowDao;

    @Override
    public String getSlideshow(Slideshow slideshow) {
        logger.info("Request getSlideshow parameter:{}", slideshow.toString());
        Map<String,Object> map = new HashMap<>();
        try {
            List<Slideshow> slideshowList = slideshowDao.selectSlideShow(slideshow);
            if (slideshowList==null||slideshowList.size()<=0){
                throw new Exception("GetSlideshow The result is empty");
            }
            map.put("list",slideshowList);
            return super.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.SLIDESHOW_SELECT_ERROR), e.getMessage());
            return super.getResult(StatusCode.SLIDESHOW_SELECT_ERROR,null);
        }
    }

    @Override
    public String insertSlideshow(Slideshow slideshow) {
        logger.info("Request insertSlideshow parameter:{}", slideshow.toString());
        try {
            slideshow.setCreateTime(new Date());
            slideshowDao.insertSlideshow(slideshow);
            return super.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture insertSlideshow ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.SLIDESHOW_INSERT_ERROR), e.getMessage());
            return super.getResult(StatusCode.SLIDESHOW_INSERT_ERROR,null);
        }
    }

    @Override
    public String updateSlideshow(Slideshow slideshow) {
        logger.info("Request updateSlideshow parameter:{}", slideshow.toString());
        try {
            slideshowDao.updateSlideshow(slideshow);
            return super.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture updateSlideshow ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.SLIDESHOW_UPDATE_ERROR), e.getMessage());
            return super.getResult(StatusCode.SLIDESHOW_UPDATE_ERROR,null);
        }
    }
}

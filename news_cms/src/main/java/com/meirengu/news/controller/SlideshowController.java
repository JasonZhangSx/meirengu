package com.meirengu.news.controller;

import com.meirengu.news.model.Slideshow;
import com.meirengu.news.service.SlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/25 16:36.
 */
@Controller
@RequestMapping("/Slideshow")
public class SlideshowController {
    @Autowired
    private SlideshowService slideshowService;

    /**
     * 条件查询轮播图
     * @param slideshow
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getBrandData(Slideshow slideshow) {
        return slideshowService.getSlideshow(slideshow);
    }

    /**
     * 条件添加轮播图
     * @param slideshow 轮播图Model
     * @return 将结果以json格式返回
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addDoctorData(Slideshow slideshow){
        return slideshowService.insertSlideshow(slideshow);
    }
    /**
     * 修改轮播图数据
     * @param slideshow
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String updateHospital(Slideshow slideshow){
        return slideshowService.updateSlideshow(slideshow);
    }

    /**
     * 删除轮播图数据
     * @param showId 轮播图ID
     * @return 将结果以json格式返回
     */
    @RequestMapping(value = "/{showId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteHospital(@PathVariable int showId) {
        Slideshow slideshow = new Slideshow();
        slideshow.setId(showId);
        slideshow.setStatus(0);
        return slideshowService.updateSlideshow(slideshow);
    }
}

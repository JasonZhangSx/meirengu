package com.meirengu.webview.controller;

import com.meirengu.webview.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 活动控制类
 *
 * @author Marvin
 * @create 2017-03-30 下午5:48
 */
@Controller
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "activities", method = RequestMethod.GET)
    public ModelAndView activities(){
        return new ModelAndView("activities", activityService.activities());
    }
}

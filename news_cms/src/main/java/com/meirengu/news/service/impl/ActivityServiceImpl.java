package com.meirengu.news.service.impl;

import com.meirengu.news.dao.ActivityDao;
import com.meirengu.news.model.Activity;
import com.meirengu.news.service.ActivityService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huoyan403 on 3/22/2017.
 */
@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService{

    @Autowired
    private ActivityDao activityDao;

}

package com.meirengu.news.service;

import com.meirengu.news.model.Feedback;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 意见反馈服务接口类
 * Created by maoruxin on 2017/3/10.
 */
public interface FeedbackService extends PageBaseService<Feedback>{


    /**
     * 新增反馈
     * @param feedback
     * @return
     */
    int insert(Feedback feedback);
    /**
     * 更新意见反馈
     * @param feedback
     * @return
     */
    public int update(Feedback feedback);

    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    int getTotalCount(Map map);





}

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
     * @param feedbackContent
     * @param userId
     * @param userName
     * @param userPhone
     * @return
     */
    int insert(String feedbackContent,Integer userId,String userName,String userPhone) throws Exception;

    /**
     * 修改反馈
     * @param feedbackId
     * @return
     */
    int updateStatus(int feedbackId) throws Exception;

    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    int getTotalCount(Map map);





}

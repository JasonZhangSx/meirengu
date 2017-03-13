package com.meirengu.news.service.impl;



import com.meirengu.news.model.Page;
import com.meirengu.news.service.FeedbackService;
import com.meirengu.news.service.PageService;
import com.meirengu.news.dao.FeedbackDao;
import com.meirengu.news.model.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 意见反馈服务类
 * Created by maoruxin on 2017/3/1.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{

    private static Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private PageService<Feedback> pageService;

    /**
     *新增意见反馈
     * @param feedbackContent
     * @param userId
     * @param userName
     * @param userPhone
     * @return
     * @throws Exception
     */
    @Override
    public int insert(String feedbackContent,Integer userId,String userName,String userPhone) throws Exception{
        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(feedbackContent);
        feedback.setUserId(userId);
        feedback.setUserName(userName);
        feedback.setUserPhone(userPhone);
        try{
            return feedbackDao.insert(feedback);
        }catch (Exception e){
            throw new Exception("insert article faile:", e);
        }
    }

    @Override
    public int updateStatus(int feedbackId) throws Exception {
        try{
            return feedbackDao.updateStatus(feedbackId);
        }catch (Exception e){
            throw new Exception("update article faile:", e);
        }
    }

    @Override
    public Page<Feedback> getPageList(Page<Feedback> page, Map map) {
        return pageService.getListByPage(page, map, feedbackDao);
    }

    @Override
    public List<Map<String, Object>> getList(Map map) {
        return pageService.getList(map, feedbackDao);
    }

    public int getTotalCount(Map map){
        return feedbackDao.getTotalCount(map);
    }

}

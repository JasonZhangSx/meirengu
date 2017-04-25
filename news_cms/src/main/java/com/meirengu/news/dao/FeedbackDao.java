package com.meirengu.news.dao;
import com.meirengu.news.model.Feedback;
import org.springframework.stereotype.Repository;

/**
 * 意见反馈数据操作
 * Created by maoruxin on 2017/3/10.
 */
@Repository
public interface FeedbackDao extends PageDao<Feedback>{

    /**
     * 新增反馈
     * @param feedback
     * @return
     */
    public int insert(Feedback feedback);

    /**
     * 更新反馈意见状态
     * @param feedbackId
     * @return
     */
    public int updateStatus(int feedbackId);

    /**
     * 更新意见反馈
     * @param feedback
     * @return
     */
    public int update(Feedback feedback);
}

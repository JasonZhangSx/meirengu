package com.meirengu.webview.service;

import com.meirengu.webview.model.Feedback;

/**
 * @author Marvin
 * @create 2017-03-30 下午7:47
 */
public interface FeedbackService {

    /**
     * 意见反馈提及
     * @param feedback
     * @return
     */
    public int feedback(Feedback feedback);
}

package com.meirengu.webview.controller;

import com.meirengu.webview.model.Feedback;
import com.meirengu.webview.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 意见反馈控制类
 *
 * @author Marvin
 * @create 2017-03-30 下午7:46
 */
@Controller
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "feedback", method = RequestMethod.GET)
    public String index(){
        return "feedback";
    }

    @ResponseBody
    @RequestMapping(value = "feedback", method = RequestMethod.POST)
    public String feedback(@RequestParam(value = "feedback_content") String feedbackContent){
        Feedback feedback =new Feedback();
        feedback.setFeedbackContent(encodeStr(feedbackContent));
        feedback.setUserId(222);
        feedback.setUserName("nini");
        feedback.setUserPhone("13811930842");
        boolean result = feedbackService.feedback(feedback);
        if (result){
            return "谢谢您宝贵的意见,我们会第一时间为您解决。";
        }else {
            return "服务器开小差了,请稍后再试!";
        }
    }

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

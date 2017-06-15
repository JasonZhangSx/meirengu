package com.meirengu.webview.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.webview.model.Feedback;
import com.meirengu.webview.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "feedback", method = RequestMethod.POST)
    public ModelAndView feedback(@RequestParam(value = "feedback_content") String feedbackContent,
                                 @RequestParam(value = "user_id") Integer userId,
                                 @RequestParam(value = "user_name") String userName,
                                 @RequestParam(value = "user_phone") String userPhone){
        Feedback feedback =new Feedback();
        feedback.setFeedbackContent(encodeStr(feedbackContent));
        feedback.setUserId(userId == null ? 0 : userId);
        feedback.setUserName(userName);
        feedback.setUserPhone(userPhone);
        int result = feedbackService.feedback(feedback);
        Map<String, Object> map = new HashMap<>();
        if (result == StatusCode.OK){
            return new ModelAndView("submit_success", map);
        }else if(result == StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF){
            map.put("errorMsg", StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF));
            return new ModelAndView("submit_fail", map);
        }else if(result == StatusCode.FEEDBACK_CONTENT_OUTOF){
            map.put("errorMsg", StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_CONTENT_OUTOF));
            return new ModelAndView("submit_fail", map);
        }else{
            return new ModelAndView("submit_fail", map);
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

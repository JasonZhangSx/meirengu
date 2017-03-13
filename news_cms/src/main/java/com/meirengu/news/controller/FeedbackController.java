package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.news.model.Article;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.FeedbackService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.meirengu.model.Result;
import com.meirengu.news.model.Feedback;

import java.util.HashMap;
import java.util.Map;


/**
 * 意见反馈控制类
 * Created by maoruxin on 2017/3/10.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert (@RequestParam(value = "feedback_content", required = false) String feedbackContent,
                                      @RequestParam(value = "user_id", required = false) Integer userId,
                                      @RequestParam(value = "user_name", required = false) String userName,
                                      @RequestParam(value = "user_phone", required = false) String userPhone
                                      ){
        if(StringUtils.isEmpty(feedbackContent)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(userId==null && userId==0){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("userId",userId);
        int feedbackNum = feedbackService.getTotalCount(paramMap);
        if(feedbackNum > 5){
            return setResult(StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF, null, StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF));
        }
        if(StringUtils.isEmpty(userName)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(StringUtils.isEmpty(userPhone)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(feedbackContent.length()>200){
            return setResult(StatusCode.FEEDBACK_CONTENT_OUTOF, null, StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_CONTENT_OUTOF));
        }

        try{
            int i = feedbackService.insert(feedbackContent,userId,userName,userPhone);
            if(i > 0){
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.FEEDBACK_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

}

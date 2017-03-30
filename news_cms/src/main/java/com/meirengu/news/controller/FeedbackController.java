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

    /**
     * 意见反馈新增
     * @param feedbackContent
     * @param userId
     * @param userName
     * @param userPhone
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result insert (@RequestParam(value = "feedback_content", required = false) String feedbackContent,
                                      @RequestParam(value = "user_id", required = false) Integer userId,
                                      @RequestParam(value = "user_name", required = false) String userName,
                                      @RequestParam(value = "user_phone", required = false) String userPhone
                                      ){
        if(StringUtils.isEmpty(feedbackContent) || userId==null || userId==0
                || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone)){
            return setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        Map paramMap = new HashMap<String, Object>();
        paramMap.put("userId",userId);
        int feedbackNum = feedbackService.getTotalCount(paramMap);
        if(feedbackNum > 5){
            return setResult(StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF, null, StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_CONTENT_COUNT_OUTOF));
        }

        if(feedbackContent.length()>200){
            return setResult(StatusCode.FEEDBACK_CONTENT_OUTOF, null, StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_CONTENT_OUTOF));
        }

        try{
            int i = feedbackService.insert(feedbackContent,userId,userName,userPhone);
            if(i == 1){
                return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return setResult(StatusCode.FEEDBACK_ERROR_INSERT, null, StatusCode.codeMsgMap.get(StatusCode.FEEDBACK_ERROR_INSERT));
            }
        }catch (Exception e){
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 用户反馈列表
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param userId
     * @param userPhone
     * @param status
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getPage(@RequestParam(value = "page_num", required = false,  defaultValue = "1") int pageNum,
                           @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
                           @RequestParam(value = "sort_by", required = false) String sortBy,
                           @RequestParam(value = "order", required = false) String order,
                           @RequestParam(value = "user_id", required = false) Integer userId,
                           @RequestParam(value = "user_phone", required = false) String userPhone,
                           @RequestParam(value = "status", required = false) Integer status) {
        Map paramMap = new HashMap<String, Object>();
        Page<Feedback> page = super.setPageParams(pageNum, pageSize);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        paramMap.put("userId", userId);
        paramMap.put("userPhone", userPhone);
        paramMap.put("status", status);
        try {
            page = feedbackService.getPageList(page, paramMap);
            return setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.error("throw exception:", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }


}

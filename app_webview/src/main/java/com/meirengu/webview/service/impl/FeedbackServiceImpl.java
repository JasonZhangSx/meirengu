package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.model.Feedback;
import com.meirengu.webview.service.FeedbackService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈接口实现类
 *
 * @author Marvin
 * @create 2017-03-30 下午7:48
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Override
    public int feedback(Feedback feedback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("feedback_content", feedback.getFeedbackContent());
        paramsMap.put("user_id", feedback.getUserId()+"");
        paramsMap.put("user_name", feedback.getUserName());
        paramsMap.put("user_phone", feedback.getUserPhone());
        String url = ConfigUtil.getConfig("URI_FEEDBACK");
        logger.info("feedback http >> url:{}, params:{}", new Object[]{url, paramsMap});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doPostForm(url, paramsMap);
        } catch (Exception e) {
            logger.error("feedback http exception << url:{},  params:{}, exception:{}", new Object[]{url, paramsMap, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("feedback result success << url:{}, params:{}, data:{}", new Object[]{url, paramsMap, data});
            }else {
                logger.info("feedback result failure << url:{}, params:{}, code:{}", new Object[]{url, paramsMap, code});
            }
            return code;
        }else{
            logger.error("feedback http failure << url:{},  params:{}, http_code:{}", new Object[]{url, paramsMap, hr.getStatusCode()});
            return hr.getStatusCode();
        }
    }
}

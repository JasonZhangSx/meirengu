package com.meirengu.erp.controller.cms;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.DateAndTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 4/6/2017.
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);


    @RequestMapping("toadd")
    public ModelAndView userList(){
        return new ModelAndView("/cms/addactivity");
    }

    @RequestMapping(method = {RequestMethod.POST})
    public ModelAndView insert(@RequestParam(value = "activity_type")Integer activityType,
                               @RequestParam(value = "activity_name")String activityName,
                               @RequestParam(value = "activity_image")String activityImage,
                               @RequestParam(value = "activity_link")String activityLink,
                               @RequestParam(value = "activity_sort")Integer activitySort,
                               @RequestParam(value = "status")Integer status,
                               @RequestParam(value = "start_time")String startTime,
                               @RequestParam(value = "end_time")String endTime
//                               @RequestParam(value = "operate_account")String operateAccount
                               ){

        try {
            DateAndTime.convertStringToDate(startTime);
            DateAndTime.convertStringToDate(endTime);
        }catch (Exception e){

        }
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("createTime",new Date()+"");
        paramsMap.put("updateTime",new Date()+"");
//        paramsMap.put("operateAccount",operateAccount+"");
        paramsMap.put("endTime",endTime+"");
        paramsMap.put("startTime",startTime+"");
        paramsMap.put("activityLink",activityLink);
        paramsMap.put("activityImage",activityImage);
        paramsMap.put("activityName",activityName);
        paramsMap.put("activityType",activityType+"");
        paramsMap.put("status",status+"");
        paramsMap.put("activitySort",activitySort+"");
        try {
            String url = ConfigUtil.getConfig("user.activity.insert");
            Object obj = super.httpPost(url,paramsMap);

            return new ModelAndView("/cms/activity");
        }catch (Exception e){
            logger.info("throw exception:", e);
            return new ModelAndView("/cms/activity");
        }
    }
}

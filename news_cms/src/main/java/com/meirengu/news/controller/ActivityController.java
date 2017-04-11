package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.news.model.Activity;
import com.meirengu.news.service.ActivityService;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/22/2017.
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    /**
     * @param pageNum 当前页
     * @param pageSize 每页显示的条数
     * @param sortBy 排序字段
     * @param order 升序/降序
     * @return
     */
    @RequestMapping(value = "list",method = {RequestMethod.POST})
    public Result list(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value="per_page", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value="sortby", required = false) String sortBy,
                       @RequestParam(value="status", required = false) Integer status,
                       @RequestParam(value="activity_id", required = false) String activityId,
                       @RequestParam(value="activity_name", required = false) String activityName,
                       @RequestParam(value="order", required = false) String order){
        Map paramMap = new HashMap<String, Object>();
        Page<Activity> page = super.setPageParams(pageNum,pageSize);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        paramMap.put("status", status);
        paramMap.put("activityId", activityId);
        paramMap.put("activityName", activityName);

        page = activityService.getListByPage(page, paramMap);
        try {
            if(page.getList().size() != 0){
                return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, page, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    @RequestMapping(method = {RequestMethod.GET})
    public Result detail(@RequestParam(value="activity_id", required = true) Integer activityId){

        Activity activity  = activityService.detail(activityId);
        try {
            if(!StringUtil.isEmpty(activity)){
                return super.setResult(StatusCode.OK, activity, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.RECORD_NOT_EXISTED, null, StatusCode.codeMsgMap.get(StatusCode.RECORD_NOT_EXISTED));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }


    @RequestMapping(method = {RequestMethod.PUT})
    public Result update( @RequestParam(value = "activity_id", required = true)Integer activityId,
                          @RequestParam(value = "status", required = false)Integer status,
                          @RequestParam(value = "activity_type", required = false)Integer activityType,
                          @RequestParam(value = "activity_name", required = false)String activityName,
                          @RequestParam(value = "activity_image", required = false)String activityImage,
                          @RequestParam(value = "activity_link", required = false)String activityLink,
                          @RequestParam(value = "activity_sort", required = false)Integer activitySort,
                          @RequestParam(value = "start_time", required = false)Date startTime,
                          @RequestParam(value = "end_time", required = false)Date endTime,
                          @RequestParam(value = "operate_account", required = true)String operateAccount){

        try {
            Activity activity = new Activity();
            activity.setActivityId(activityId);
            activity.setActivitySort(activitySort);
            activity.setStatus(status);
            activity.setActivityType(activityType);
            activity.setActivityName(activityName);
            activity.setActivityImage(activityImage);
            activity.setActivityLink(activityLink);
            if(!StringUtil.isEmpty(startTime)){
                activity.setStartTime(startTime);
            }
            if(!StringUtil.isEmpty(endTime)){
                activity.setEndTime(endTime);
            }
            activity.setOperateAccount(operateAccount);
            activity.setUpdateTime(new Date());
            int result  = activityService.update(activity);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    @RequestMapping(value = "/{activityId}",method = {RequestMethod.DELETE})
    public Result delete(@PathVariable Integer activityId){
        int result  = activityService.delete(activityId);
        try {
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }

    @RequestMapping(value = "insert",method = {RequestMethod.POST})
    public Result insert(@RequestParam(value = "activity_type", required = true)Integer activityType,
                         @RequestParam(value = "activity_name", required = true)String activityName,
                         @RequestParam(value = "activity_image", required = true)String activityImage,
                         @RequestParam(value = "activity_link", required = true)String activityLink,
                         @RequestParam(value = "activity_sort", required = true)Integer activitySort,
                         @RequestParam(value = "remarks", required = false ,defaultValue = "")String remarks,
                         @RequestParam(value = "status", required = true)Integer status,
                         @RequestParam(value = "start_time", required = true)Date startTime,
                         @RequestParam(value = "end_time", required = true)Date endTime,
                         @RequestParam(value = "operate_account", required = true)String operateAccount){

        try {
            Activity activity = new Activity();
            activity.setActivitySort(activitySort);
            activity.setStatus(status);
            activity.setActivityType(activityType);
            activity.setActivityName(activityName);
            activity.setActivityImage(activityImage);
            activity.setActivityLink(activityLink);
            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
            activity.setOperateAccount(operateAccount);
            activity.setCreateTime(new Date());
            activity.setUpdateTime(new Date());
            activity.setRemarks(remarks);
            int result = activityService.insert(activity);
            if(result != 0){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else{
                return super.setResult(StatusCode.UNKNOWN_EXCEPTION, null, StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return super.setResult(StatusCode.UNKNOWN_EXCEPTION, e.getMessage(), StatusCode.codeMsgMap.get(StatusCode.UNKNOWN_EXCEPTION));
        }
    }
    /**
     * 格式化string类型时间
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

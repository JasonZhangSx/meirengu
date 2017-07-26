package com.meirengu.erp.controller.cms;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.DatatablesViewPage;
import com.meirengu.common.StatusCode;
import com.meirengu.commons.authority.common.enums.OperationTypeEnum;
import com.meirengu.commons.authority.model.Account;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huoyan403 on 4/6/2017.
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);


    @RequestMapping("toadd")
    public ModelAndView toadd(){
        Map map = new HashedMap();
        map.put("date", DateAndTime.getCurrentDateTime());
        return new ModelAndView("/cms/addactivity",map);
    }
    @RequestMapping("toedit")
    public ModelAndView toedit(@RequestParam(value="activity_id", required = false ,defaultValue = "") String activityId){

        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("news.activity.detail");
        String urlAppend = url+"?activity_id="+activityId;
        try {
            map = ( Map<String, Object>)super.httpGet(urlAppend);
            map.put("activityId",activityId);
            String[] imageList = String.valueOf(map.get("activityImage")).split(",");
            List<String> stringList = new ArrayList<>();
            for (String image :imageList){
                stringList.add(image);
            }
            map.put("stringList",stringList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/cms/editactivity", map);
    }
    @RequestMapping("tolist")
    public ModelAndView tolist(){
        return new ModelAndView("/cms/activity");
    }

    @RequestMapping(method = {RequestMethod.POST})
    @ResponseBody
    public Result insert(@RequestParam(value = "activity_type")Integer activityType,
                         @RequestParam(value = "activity_name")String activityName,
                         @RequestParam(value = "activity_image")String activityImage,
                         @RequestParam(value = "activity_link")String activityLink,
                         @RequestParam(value = "activity_sort")Integer activitySort,
                         @RequestParam(value = "remarks")String remarks,
                         @RequestParam(value = "status")Integer status,
                         @RequestParam(value = "start_time")String startTime,
                         @RequestParam(value = "end_time")String endTime){
        try {
            addLogOperation("活动信息新增", OperationTypeEnum.INSERT.getIndex(),"","" +
                    "activityType||"+activityType+",activityName||"+activityName+",activityImage||"+activityImage+",activityLink||"+activityLink+"" +
                    ",activitySort||"+activitySort+",remarks||"+remarks+",status||"+status+",startTime||"+startTime+",endTime||"+endTime+"");


            Map<String,String> paramsMap = new HashMap<String,String>();
            paramsMap.put("operate_account", ((Account)(SecurityUtils.getSubject().getPrincipal())).getUserName());
            paramsMap.put("end_time",endTime+"");
            paramsMap.put("start_time",startTime+"");
            paramsMap.put("activity_link",activityLink);
            paramsMap.put("activity_image",activityImage);
            paramsMap.put("activity_name",activityName);
            paramsMap.put("activity_type",activityType+"");
            paramsMap.put("status",status+"");
            paramsMap.put("activity_sort",activitySort+"");
            paramsMap.put("remarks",remarks);
            String url = ConfigUtil.getConfig("news.activity.insert");

            HttpUtil.HttpResult hr = HttpUtil.doPostForm(url, paramsMap);
            logger.debug("Request: {} getResponse: {}", url, hr);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer code = jsonObject.getIntValue("code");
                if(code != null && code == StatusCode.OK){
                    return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    return setResult(code, null, StatusCode.codeMsgMap.get(code));
                }
            } else {
                return setResult(statusCode, null, StatusCode.codeMsgMap.get(statusCode));
            }
        }catch (Exception e){
            logger.info("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 分页查看活动详情
     * @param request
     * @param activityName
     * @return
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> list(HttpServletRequest request,
                                 @RequestParam(value="activity_id", required = false ,defaultValue = "") String activityId,
                                 @RequestParam(value="status", required = false ,defaultValue = "") String status,
                                 @RequestParam(value="activity_type", required = false ,defaultValue = "") String activityType,
                                 @RequestParam(value="activity_name", required = false ,defaultValue = "") String activityName){
        addLogOperation("活动信息查看", OperationTypeEnum.SELECT.getIndex(),"","");

        Map<String,String> paramsMap = new HashedMap();
        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("news.activity.list");
        //查询参数
        paramsMap.put("activity_id",activityId);
        paramsMap.put("activity_name",activityName);
        paramsMap.put("activity_type",activityType);
        paramsMap.put("status",status);
        /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
        int page = Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length"))+ 1;
        paramsMap.put("page",page+"");
        paramsMap.put("per_page",request.getParameter("length"));

        map = (Map<String,Object>)super.httpPost(url,paramsMap);

        //封装返回集合
        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        List<Map<String,Object>> activityList = (List<Map<String,Object>>) map.get("list");

        //保存给datatabls 分页数据
        view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
        view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录

        view.setAaData(activityList);
        return view;
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update( @RequestParam(value="activity_id", required = true ) String activityId,
                       @RequestParam(value = "activity_type" , required = false)Integer activityType,
                       @RequestParam(value = "activity_name", required = false)String activityName,
                       @RequestParam(value = "activity_image", required = false)String activityImage,
                       @RequestParam(value = "activity_link", required = false)String activityLink,
                       @RequestParam(value = "activity_sort", required = false)Integer activitySort,
                       @RequestParam(value = "remarks", required = false)String remarks,
                       @RequestParam(value = "status", required = false)String status,
                       @RequestParam(value = "start_time", required = false)Date startTime,
                       @RequestParam(value = "end_time", required = false)Date endTime){

        Map<String,Object> map = new HashedMap();
        Map<String,String> paramsMap = new HashedMap();
        try {
            addLogOperation("活动信息修改", OperationTypeEnum.UPDATE.getIndex(),activityId,"" +
                    "activityType||"+activityType+",activityName||"+activityName+",activityImage||"+activityImage+",activityLink||"+activityLink+"" +
                    ",activitySort||"+activitySort+",remarks||"+remarks+",status||"+status+",startTime||"+startTime+",endTime||"+endTime+"");

            paramsMap.put("activity_id",activityId);
            paramsMap.put("operate_account",((Account)(SecurityUtils.getSubject().getPrincipal())).getUserName());
            if(!StringUtil.isEmpty(endTime)){
                paramsMap.put("end_time", DateAndTime.convertDateToString(endTime,"yyyy-MM-dd HH:mm:ss"));
            }if(!StringUtil.isEmpty(startTime)){
                paramsMap.put("start_time",DateAndTime.convertDateToString(startTime,"yyyy-MM-dd HH:mm:ss"));
            }
            if(activityLink!=null){
                paramsMap.put("activity_link",activityLink);
            }
            if(activityImage!=null){
                paramsMap.put("activity_image",activityImage);
            }
            if(activityName!=null){
                paramsMap.put("activity_name",activityName);
            }
            if(activityType!=null){
                paramsMap.put("activity_type",activityType+"");
            }
            if(activitySort!=null){
                paramsMap.put("activity_sort",activitySort+"");
            }
            if(remarks!=null){
                paramsMap.put("remarks",remarks);
            }
            if(status!=null){
                paramsMap.put("status",status);
            }
            String url = ConfigUtil.getConfig("news.activity.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            logger.debug("Request: {} getResponse: {}", url, hr);
            int statusCode = hr.getStatusCode();
            if(statusCode == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer code = jsonObject.getIntValue("code");
                if(code != null && code == StatusCode.OK){
                    return setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    return setResult(code, null, StatusCode.codeMsgMap.get(code));
                }
            } else {
                return setResult(statusCode, null, StatusCode.codeMsgMap.get(statusCode));
            }
        } catch (Exception e) {
            logger.error("throw exception:{}", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
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

package com.meirengu.erp.controller.cms;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.DatatablesViewPage;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huoyan403 on 4/6/2017.
 */
@RestController
@RequestMapping("faqclass")
public class FaqClassController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(FaqClassController.class);


    @RequestMapping("toadd")
    public ModelAndView toadd(){
        return new ModelAndView("/cms/addfaqclass");
    }
    @RequestMapping("toedit")
    public ModelAndView toedit(@RequestParam(value="class_id", required = false ,defaultValue = "") String classId){

        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("news.faqclass.detail");
        String urlAppend = url+"?class_id="+classId;
        try {
            map = ( Map<String, Object>)super.httpGet(urlAppend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/cms/editfaqclass", map);
    }
    @RequestMapping("tolist")
    public ModelAndView tolist(){
        return new ModelAndView("/cms/faqclass");
    }

    @RequestMapping(method = {RequestMethod.POST})
    @ResponseBody
    public Result insert(@RequestParam(value = "class_name")String className)
//                             @RequestParam(value = "operate_account")String operateAccount
    {
        try {
            Map<String,String> paramsMap = new HashMap<String,String>();
            paramsMap.put("operate_account","admin");
            paramsMap.put("class_name",className);
            String url = ConfigUtil.getConfig("news.faqclass.insert");
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
            logger.error("throw exception:", e);
            return setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> list(HttpServletRequest request,
                                 @RequestParam(value="class_id", required = false) String classId,
                                 @RequestParam(value="start", required = false) Integer start,
                                 @RequestParam(value="length", required = false) Integer length){

        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        try {
            Map<String, Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("news.faqclass.list");
            //查询参数
            /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
            int page = start/length + 1;
            String urlAppend = url+"?page="+page+"&per_page="+length;
            if(classId!=null){
                urlAppend = "&class_id="+classId;
            }
            map = (Map<String,Object>)super.httpGet(urlAppend);
            List<Map<String,Object>> activityList = new ArrayList<>();
            if(map != null){
                //封装返回集合
                activityList = (List<Map<String,Object>>) map.get("list");
                //保存给datatabls 分页数据
                view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
                view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录
            }else{
                view.setiTotalDisplayRecords(0);
                view.setiTotalRecords(0);
            }
            view.setAaData(activityList);
        } catch (IOException e) {
            logger.info("FaqClass list page throws Exception :{}" ,e);
        }
        return view;
    }

    @RequestMapping("update")
    @ResponseBody
    public Map update( @RequestParam(value="class_id", required = true ) String classId,
                       @RequestParam(value = "status" , required = false)String status){

        Map<String,Object> map = new HashedMap();
        Map<String,String> paramsMap = new HashedMap();
        try {
            paramsMap.put("class_id",classId);
            paramsMap.put("operate_account","admin");
            if(status!=null){
                paramsMap.put("status",status);
            }
            String url = ConfigUtil.getConfig("news.faqclass.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            map.put("code",hr.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public Result edit( @RequestParam(value="class_id", required = true ) String classId,
                       @RequestParam(value = "class_name" , required = false)String className){

        Map<String,Object> map = new HashedMap();
        Map<String,String> paramsMap = new HashedMap();
        try {
            paramsMap.put("class_id",classId);
            paramsMap.put("operate_account","admin");
            if(className!=null){
                paramsMap.put("class_name",className);
            }
            String url = ConfigUtil.getConfig("news.faqclass.update");
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

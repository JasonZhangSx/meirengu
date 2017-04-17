package com.meirengu.erp.controller.cms;

import com.meirengu.common.DatatablesViewPage;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView insert(@RequestParam(value = "class_name")String className
//                             @RequestParam(value = "operate_account")String operateAccount
                               ){
        try {
            Map<String,String> paramsMap = new HashMap<String,String>();
            paramsMap.put("operate_account","admin");
            paramsMap.put("class_name",className);
            String url = ConfigUtil.getConfig("news.faqclass.insert");
            Object obj = super.httpPost(url,paramsMap);
            //todo 做返回处理
            return new ModelAndView("/cms/faqclass");
        }catch (Exception e){
            logger.info("throw exception:", e);
            return new ModelAndView("/cms/faqclass");
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
            String urlAppend = url+"?page="+page+"&per_page="+length+"&class_id="+classId;
            map = (Map<String,Object>)super.httpGet(urlAppend);
            //封装返回集合
            List<Map<String,Object>> activityList = (List<Map<String,Object>>) map.get("list");
            //保存给datatabls 分页数据
            view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
            view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录
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
            String url = ConfigUtil.getConfig("news.faqclass.status.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            map.put("code",hr.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("edit")
    public ModelAndView edit( @RequestParam(value="class_id", required = true ) String classId,
                       @RequestParam(value = "class_name" , required = false)String className){

        Map<String,Object> map = new HashedMap();
        Map<String,String> paramsMap = new HashedMap();
        try {
            paramsMap.put("class_id",classId);
            paramsMap.put("operate_account","admin");
            if(className!=null){
                paramsMap.put("status",className);
            }
            String url = ConfigUtil.getConfig("news.faqclass.status.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            if(hr.getStatusCode()==200){
                return new ModelAndView("/cms/faqclass");
            }else{
                return new ModelAndView("redirect:/faqclass/toedit?class_id="+classId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

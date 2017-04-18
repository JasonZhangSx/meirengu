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
import java.util.*;

/**
 * Created by huoyan403 on 4/6/2017.
 */
@RestController
@RequestMapping("faq")
public class FaqController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(FaqController.class);


    @RequestMapping("toadd")
    public ModelAndView toadd(){
        List<Map<String, Object>> list = new ArrayList<>();
        String url = ConfigUtil.getConfig("news.faqclass.listall");
        try {
            list = ( List<Map<String, Object>>)super.httpGet(url+"?status=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("list",list);
        return new ModelAndView("/cms/addfaq", map);
    }
    @RequestMapping("toedit")
    public ModelAndView toedit(@RequestParam(value="class_id", required = false ,defaultValue = "") String classId){

        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("news.faq.detail");
        String urlAppend = url+"?class_id="+classId;
        try {
            map = ( Map<String, Object>)super.httpGet(urlAppend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/cms/editfaq", map);
    }
    @RequestMapping("tolist")
    public ModelAndView tolist(){
        return new ModelAndView("/cms/faq");
    }

    /**
     * 添加常见问题
     * @param className
     * @return
     */
    @RequestMapping(method = {RequestMethod.POST})
    public ModelAndView insert(@RequestParam(value = "class_id")String classId,
                               @RequestParam(value = "class_name")String className,
                               @RequestParam(value = "faq_question")String faqQuestion,
                               @RequestParam(value = "faq_answer")String faqAnswer
//                             @RequestParam(value = "operate_account")String operateAccount
                               ){
        try {
            Map<String,String> paramsMap = new HashMap<String,String>();
            paramsMap.put("operate_account","admin");
            paramsMap.put("class_id",classId);
            paramsMap.put("class_name",className);
            paramsMap.put("faq_question",faqQuestion);
            paramsMap.put("faq_answer",faqAnswer);
            String url = ConfigUtil.getConfig("news.faq.insert");
            Object obj = super.httpPost(url,paramsMap);
            //todo 做返回处理
            return new ModelAndView("/cms/faq");
        }catch (Exception e){
            logger.info("throw exception:", e);
            return new ModelAndView("/cms/faq");
        }
    }

    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> list(HttpServletRequest request,
                                 @RequestParam(value="faq_id", required = false) String faqId,
                                 @RequestParam(value="start", required = false) Integer start,
                                 @RequestParam(value="length", required = false) Integer length){

        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        try {
            Map<String, Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("news.faq.list");
            //查询参数
            /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
            int page = start/length + 1;
            String urlAppend = url+"?page="+page+"&per_page="+length;
            if(faqId!=null){
                urlAppend = "&faq_id="+faqId;
            }
            map = (Map<String,Object>)super.httpGet(urlAppend);
            //封装返回集合
            List<Map<String,Object>> activityList = (List<Map<String,Object>>) map.get("list");
            //保存给datatabls 分页数据
            view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
            view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录
            view.setAaData(activityList);
        } catch (IOException e) {
            logger.info("faq list page throws Exception :{}" ,e);
        }
        return view;
    }

    @RequestMapping("update")
    @ResponseBody
    public Map update( @RequestParam(value="faq_id", required = true ) String faqId,
                       @RequestParam(value = "status" , required = false)String status){

        Map<String,Object> map = new HashedMap();
        Map<String,String> paramsMap = new HashedMap();
        try {
            paramsMap.put("faq_id",faqId);
            paramsMap.put("operate_account","admin");
            paramsMap.put("faq_question","");
            paramsMap.put("faq_answer","");
            paramsMap.put("class_id","");
            paramsMap.put("class_name","");
            if(status!=null){
                paramsMap.put("status",status);
            }
            String url = ConfigUtil.getConfig("news.faq.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            map.put("code",hr.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("edit")
    public ModelAndView edit( @RequestParam(value="faq_id", required = true ) String faqId,
                       @RequestParam(value = "class_name" , required = false)String className){

        Map<String,Object> map = new HashedMap();
        Map<String,String> paramsMap = new HashedMap();
        try {
            paramsMap.put("faq_id",faqId);
            paramsMap.put("operate_account","admin");
            if(className!=null){
                paramsMap.put("class_name",className);
            }
            String url = ConfigUtil.getConfig("news.faq.update");
            HttpUtil.HttpResult hr = HttpUtil.doPut(url, paramsMap);
            if(hr.getStatusCode()==200){
                return new ModelAndView("/cms/faq");
            }else{
                return new ModelAndView("redirect:/faq/toedit?faq_id="+faqId);
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

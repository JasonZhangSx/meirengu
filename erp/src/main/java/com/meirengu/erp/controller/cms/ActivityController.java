package com.meirengu.erp.controller.cms;

import com.meirengu.common.DatatablesViewPage;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 4/6/2017.
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);


    @RequestMapping("toadd")
    public ModelAndView toadd(){
        return new ModelAndView("/cms/addactivity");
    }
    @RequestMapping("tolist")
    public ModelAndView tolist(){
        return new ModelAndView("/cms/activity");
    }

    @RequestMapping(method = {RequestMethod.POST})
    public ModelAndView insert(@RequestParam(value = "activity_type")Integer activityType,
                               @RequestParam(value = "activity_name")String activityName,
                               @RequestParam(value = "activity_image")String activityImage,
                               @RequestParam(value = "activity_link")String activityLink,
                               @RequestParam(value = "activity_sort")Integer activitySort,
                               @RequestParam(value = "remarks")String remarks,
                               @RequestParam(value = "status")Integer status,
                               @RequestParam(value = "start_time")String startTime,
                               @RequestParam(value = "end_time")String endTime
//                             @RequestParam(value = "operate_account")String operateAccount
                               ){
        try {
            Map<String,String> paramsMap = new HashMap<String,String>();
            paramsMap.put("operate_account","暂时保留");
            paramsMap.put("end_time",endTime+"");
            paramsMap.put("start_time",startTime+"");
            paramsMap.put("activity_link",activityLink);
            paramsMap.put("activity_image",activityImage);
            paramsMap.put("activity_name",activityName);
            paramsMap.put("activity_type",activityType+"");
            paramsMap.put("status",status+"");
            paramsMap.put("activity_sort",activitySort+"");
            paramsMap.put("remarks",remarks);
            String url = ConfigUtil.getConfig("user.activity.insert");
            Object obj = super.httpPost(url,paramsMap);
            //todo 做返回处理
            return new ModelAndView("/cms/activity");
        }catch (Exception e){
            logger.info("throw exception:", e);
            return new ModelAndView("/cms/activity");
        }
    }

    /**
     * 分页查看活动详情
     * @param request
     * @param id
     * @param activityName
     * @return
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> list(HttpServletRequest request,
                                 @RequestParam(value="id", required = false ,defaultValue = "") String id,
                                 @RequestParam(value="activity_name", required = false ,defaultValue = "") String activityName){

        Map<String,String> paramsMap = new HashedMap();
        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("user.activity.list");
        //查询参数
        paramsMap.put("id",id);
        paramsMap.put("activity_name",activityName);
        paramsMap.put("activity_name",activityName);
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
}

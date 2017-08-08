package com.meirengu.erp.controller.user;

import com.meirengu.common.DatatablesViewPage;
import com.meirengu.commons.authority.common.enums.OperationTypeEnum;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.erp.utils.ExportExcel;
import com.meirengu.erp.utils.InfoProcessUtil;
import com.meirengu.erp.vo.InviterVo;
import com.meirengu.utils.ApacheBeanUtils;
import com.meirengu.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 4/6/2017.
 */
@RestController
@RequestMapping("inviter")
public class InviterController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("tolist")
    public ModelAndView userList(){
        return new ModelAndView("/user/userInvite");
    }

    /**
     * 分页查看邀请人信息数据
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> datatablesTest(HttpServletRequest request,
                                 @RequestParam(value="phone", required = false ,defaultValue = "") String phone,
                                 @RequestParam(value="invite_phone", required = false ,defaultValue = "") String invitePhone,
                                 @RequestParam(value="invite_idcard", required = false ,defaultValue = "") String inviteIdcard,
                                 @RequestParam(value="invest_conditions", required = false ,defaultValue = "") String investConditions){
        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        try {
            Map<String,String> paramsMap = new HashedMap();
            Map<String, Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("user.inviter.list");
            //查询参数
            paramsMap.put("phone",phone);
            paramsMap.put("invite_phone",invitePhone);
            paramsMap.put("invite_idcard",inviteIdcard);
            paramsMap.put("invest_conditions",investConditions);
        /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
            int page = Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length"))+ 1;
            paramsMap.put("page",page+"");
            paramsMap.put("per_page",request.getParameter("length"));

            map = (Map<String,Object>)super.httpPost(url,paramsMap);
            //封装返回集合
            List<Map<String,Object>> userList = new ArrayList<Map<String, Object>>();
            if(map == null){
                //保存给datatabls 分页数据
                view.setiTotalDisplayRecords(0);//显示总记录
                view.setiTotalRecords(0);//数据库总记录
            }else{
                userList = (List<Map<String,Object>>) map.get("list");
                for(Map user:userList){
                    user.put("invitedIdCard", InfoProcessUtil.generateDefaultIdCard(String.valueOf(user.get("invitedIdCard"))));
                }
                //保存给datatabls 分页数据
                view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
                view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录
            }
            view.setAaData(userList);
            return view;
        }catch (Exception e){
            logger.error("throw exception:", e);
            return view;
        }finally {
            addLogOperation("邀请关系信息查看", OperationTypeEnum.SELECT.getIndex(),"","");
        }
    }

    @RequestMapping(value="/export", method= RequestMethod.GET)
    public ModelAndView inviterExport(HttpServletResponse response){

        try {

            Map<String,String> paramsMap = new HashedMap();
            Map<String, Object> map = new HashMap<>();
            String url = ConfigUtil.getConfig("user.inviter.list");
            //查询参数
            paramsMap.put("per_page","1000000");

            map = (Map<String,Object>)super.httpPost(url,paramsMap);
            String fileName = "inviterInfo"+ DateUtils.getFormatDate("yyyyMMddHHmmss")+".xlsx";
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("list");
            List<InviterVo> list = new ArrayList<InviterVo>();
            for(Map userMap:userList){
                InviterVo inviterVo = (InviterVo) ApacheBeanUtils.mapToObject(userMap,InviterVo.class);
                list.add(inviterVo);
            }
            new ExportExcel("", InviterVo.class).setDataList(list).write(response, fileName).dispose();
            return new ModelAndView("/user/userInvite");
        } catch (Exception e) {
            logger.error("throw exception:", e);
            return new ModelAndView("/user/userInvite");
        }finally {
            addLogOperation("邀请关系信息导出", OperationTypeEnum.EXPORT.getIndex(),"","");
        }
    }

}

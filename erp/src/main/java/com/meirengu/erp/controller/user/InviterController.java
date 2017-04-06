package com.meirengu.erp.controller.user;

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
@RequestMapping("inviter")
public class InviterController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("tolist")
    public ModelAndView userList(){
        return new ModelAndView("/user/userInvite");
    }

    /**
     * 分页查看邀请人信息数据
     * @param request
     * @param inviteRealname
     * @param realname
     * @param inviteIdcard
     * @return
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> datatablesTest(HttpServletRequest request,
                                 @RequestParam(value="realname", required = false ,defaultValue = "") String realname,
                                 @RequestParam(value="invite_realname", required = false ,defaultValue = "") String inviteRealname,
                                 @RequestParam(value="invite_idcard", required = false ,defaultValue = "") String inviteIdcard){

        Map<String,String> paramsMap = new HashedMap();
        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("user.inviter.list");
        //查询参数
        paramsMap.put("realname",realname);
        paramsMap.put("inviteRealname",inviteRealname);
        paramsMap.put("inviteIdcard",inviteIdcard);
        /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
        int page = Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length"))+ 1;
        paramsMap.put("page",page+"");
        paramsMap.put("per_page",request.getParameter("length"));

        map = (Map<String,Object>)super.httpPost(url,paramsMap);
        //封装返回集合
        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        List<Map<String,Object>> userList = (List<Map<String,Object>>) map.get("list");
//        //后台处理数据 保存编号 没有编号的不需要这一步
//        for (int i = 0;i<userList.size();i++){
//            userList.get(i).put("id",i+1);
//        }
        //保存给datatabls 分页数据
        view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
        view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录

        view.setAaData(com.meirengu.utils.ObjectUtils.getNotNullObject(userList,List.class));
        return view;
    }
}

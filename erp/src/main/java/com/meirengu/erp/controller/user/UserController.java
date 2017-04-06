package com.meirengu.erp.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.DatatablesViewPage;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
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
 * Created by huoyan403 on 3/29/2017.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("tolist")
    public ModelAndView userList(){
        return new ModelAndView("/user/userList");
    }

    /**
     * 分页查看用户信息数据
     * @param request
     * @param phone
     * @param realname
     * @param idcard
     * @return
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> datatablesTest(HttpServletRequest request,
                                    @RequestParam(value="phone", required = false ,defaultValue = "") String phone,
                                    @RequestParam(value="realname", required = false ,defaultValue = "") String realname,
                                    @RequestParam(value="idcard", required = false ,defaultValue = "") String idcard){

        Map<String,String> paramsMap = new HashedMap();
        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("user.list");
        //查询参数
        paramsMap.put("phone",phone);
        paramsMap.put("realname",realname);
        paramsMap.put("idcard",idcard);
        /*配置分页数据 datatables传递过来的是 从第几条开始 以及要查看的数据长度*/
        int page = Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length"))+ 1;
        paramsMap.put("page",page+"");
        paramsMap.put("per_page",request.getParameter("length"));

        map = (Map<String,Object>)super.httpPost(url,paramsMap);

        //封装返回集合
        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        List<Map<String,Object>> userList = (List<Map<String,Object>>) map.get("list");
        //后台处理数据 保存编号 没有编号的不需要这一步
        for (int i = 0;i<userList.size();i++){
            userList.get(i).put("id",i+1);
        }
        //保存给datatabls 分页数据
        view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
        view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录

        view.setAaData(userList);
        return view;
    }

    /**
     * 查看个人信息详情
     * @param phone
     * @return
     */
    @RequestMapping("detail")
    public ModelAndView userDetail( @RequestParam(value="phone", required = true) String phone){
        Map<String, Object> map = new HashMap<>();
        String urlForGetUser = ConfigUtil.getConfig("user.list");
        String urlForGetUserAddress = ConfigUtil.getConfig("user.address.list");
        try {
            Map<String,String> paramsMap = new HashedMap();
            Map<String,Object> mapUserInfo = new HashedMap();
            paramsMap.put("phone",phone);
            mapUserInfo = ( Map<String, Object>)super.httpPost(urlForGetUser,paramsMap);
            JSONArray objects =(JSONArray) mapUserInfo.get("list");
            Map<String,Object> user  = ( Map<String,Object>)objects.get(0);

            Map<String,String> paramsForAddress = new HashedMap();
            paramsForAddress.put("user_id",user.get("userId")+"");

            Map<String,Object> mapAddress = ( Map<String, Object>)super.httpPost(urlForGetUserAddress,paramsForAddress);

            // TODO: 3/30/2017 可能会有exception
            map.put("userInfo", ((JSONArray) mapUserInfo.get("list")).get(0));
            map.put("mapAddress",mapAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/user/userDetail", map);
    }





    public Map sendPost(String url,Map paramsMap){
        Map<String, Object> map = new HashMap<>();
        HttpUtil.HttpResult hr = HttpUtil.doPostForm(url,paramsMap);
        try {
            if(hr!=null && hr.getStatusCode() == StatusCode.OK){
                String content = hr.getContent();
                JSONObject jsonObject = JSONObject.parseObject(content);
                Object code = jsonObject.get("code");
                if(code != null){
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    map = data;
                }
            }else{
                logger.info("UserController.sendPost connected refused ! url:{}",url);
            }
        }catch (Exception e){
            logger.info("UserController.sendPost throws Exception :{}" ,e.getMessage());
        }
        return  map;
    }
}

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
public class UserController<T> extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("tolist")
    public ModelAndView userList(){
        return new ModelAndView("/user/userList");
    }

    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<Map<String,Object>> datatablesTest(HttpServletRequest request,
                                    @RequestParam(value="phone", required = false ,defaultValue = "") String phone,
                                    @RequestParam(value="realname", required = false ,defaultValue = "") String realname,
                                    @RequestParam(value="idcard", required = false ,defaultValue = "") String idcard){

    //获取前台额外传递过来的查询条件
        String extra_search = request.getParameter("extra_search");
        System.out.println(extra_search);

        Map<String,String> paramsMap = new HashedMap();
        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("user.list");
        paramsMap.put("phone",phone);
        paramsMap.put("realname",realname);
        paramsMap.put("idcard",idcard);

        int page = Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length"))+ 1;
        paramsMap.put("page",page+"");
        paramsMap.put("per_page",request.getParameter("length"));

        map = (Map<String,Object>)super.httpPost(url,paramsMap);

//        map.put("phone",phone);
//        map.put("realname",realname);
//        map.put("idcard",idcard);

//        List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
//        list.add(map);


        DatatablesViewPage<Map<String,Object>> view = new DatatablesViewPage<Map<String,Object>>();
        List<Map<String,Object>> userList = (List<Map<String,Object>>) map.get("list");
        for (int i = 0;i<userList.size();i++){
            userList.get(i).put("id",i+1);
        }
        view.setiTotalDisplayRecords(Integer.valueOf(map.get("totalCount")+""));//显示总记录
        view.setiTotalRecords(Integer.valueOf(map.get("totalCount")+""));//数据库总记录

        view.setAaData(userList);
        return view;
    }
    @ResponseBody
    @RequestMapping("listTest")
    public Map userList(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(value="per_page", required = false, defaultValue = "100") Integer pageSize,
                        @RequestParam(value="phone", required = false ,defaultValue = "") String phone,
                        @RequestParam(value="realname", required = false ,defaultValue = "") String realname,
                        @RequestParam(value="idcard", required = false ,defaultValue = "") String idcard,
                        @RequestParam(value="sortby", required = false) String sortBy,
                        @RequestParam(value="order", required = false) String order){
        Map<String, Object> map = new HashMap<>();
        String url = ConfigUtil.getConfig("user.list");
        try {
            Map<String,String> paramsMap = new HashedMap();
            paramsMap.put("phone",phone);
            paramsMap.put("realname",realname);
            paramsMap.put("idcard",idcard);
            paramsMap.put("page",pageNum+"");
            paramsMap.put("per_page",pageSize+"");
            map = (Map<String,Object>)super.httpPost(url,paramsMap);
            map.put("phone",phone);
            map.put("realname",realname);
            map.put("idcard",idcard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

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

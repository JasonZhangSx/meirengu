package com.meirengu.erp.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/29/2017.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("list")
    public ModelAndView userList(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "100") Integer pageSize,
                                    @RequestParam(value="phone", required = false) String phone,
                                    @RequestParam(value="realname", required = false) String realname,
                                    @RequestParam(value="idcard", required = false) String idcard,
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
        return new ModelAndView("/user/userList", map);
    }

    @RequestMapping("detail")
    public ModelAndView userDetail( @RequestParam(value="phone", required = true) String phone){
        Map<String, Object> map = new HashMap<>();
        String urlForGetUser = ConfigUtil.getConfig("user.list");
        String urlForGetUserAddress = ConfigUtil.getConfig("user.address.list");
        try {
            Map<String,String> paramsMap = new HashedMap();
            paramsMap.put("phone",phone);
            map = ( Map<String, Object>)super.httpPost(urlForGetUser,paramsMap);
            JSONArray objects =(JSONArray) map.get("list");
            Map<String,Object> user  = ( Map<String,Object>)objects.get(0);
//            Map<String,Object> user = JSONArray.toJavaObject(objects,Map.class);
//            Map<String,Object> user = JSONArray.parseObject();//.readValue((JSONArray)map.get("list"),Map.class);

            Map<String,String> paramsForAddress = new HashedMap();
//            paramsForAddress.put("userId",user.get("userId")+"");

            Object data = super.httpPost(urlForGetUserAddress,paramsForAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/user/userList", map);
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

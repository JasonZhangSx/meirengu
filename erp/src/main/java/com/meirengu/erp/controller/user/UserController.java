package com.meirengu.erp.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
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
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("list")
    public ModelAndView partnerList(@RequestParam(value="page", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "100") Integer pageSize,
                                    @RequestParam(value="is_page", required = false,defaultValue = "0") Integer isPage,
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
            paramsMap.put("is_page",isPage+"");
            map = this.sendPost(url,paramsMap);
            map.put("phone",phone);
            map.put("realname",realname);
            map.put("idcard",idcard);
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

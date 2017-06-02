package com.meirengu.webview.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.controller.BaseController;
import com.meirengu.utils.RequestUtil;
import com.meirengu.webview.service.SignupService;
import com.meirengu.webview.service.UserCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 海伦活动controller
 * @author 建新
 * @create 2017-05-31 11:02
 */
@Controller
@RequestMapping("helen")
public class HeleActivityController extends BaseController{

    Logger logger = LoggerFactory.getLogger(HeleActivityController.class);

    @Autowired
    UserCenterService userCenterService;
    @Autowired
    SignupService signupService;

    @RequestMapping
    public String toRegister(){
        return "helen_activity/helen_act_sc";
    }

    @ResponseBody
    @RequestMapping("register")
    public Map<String, Object> register(String mobile, String checkCode, String inviteMobile, HttpServletRequest request){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String ip = RequestUtil.getClientIp(request);
        JSONObject userMsg = userCenterService.register(mobile, checkCode, inviteMobile, 2, ip);
        if(userMsg == null){
            logger.error("HeleActivityController.register fail......");
            returnMap.put("code", "500");
            return returnMap;
        }
        String code = userMsg.get("code").toString();
        if(!code.equals("200")){
            logger.error("HeleActivityController.register return code is {}", code);
            return userMsg;
        }
        //记录报名的人员
        signupService.signup("", mobile, 2, "");
        JSONObject userData = (JSONObject) userMsg.get("data");
        JSONObject user = (JSONObject) userData.get("user");
        String token = userData.get("token") == null ? "" : userData.get("token").toString();
        String userId = user.get("userId").toString();
        //JSONObject inviteRecord = userCenterService.getInviteRecord(userId, 1, 6);
        returnMap.put("code", 200);
        returnMap.put("token", token);
        returnMap.put("userId", userId);
        returnMap.put("mobile", mobile);
        //returnMap.put("inviteRecord", inviteRecord);
        return returnMap;
    }

    @ResponseBody
    @RequestMapping("register/code")
    public Map<String, Object> getLoginCode(String mobile, HttpServletRequest request){
        String ip = RequestUtil.getClientIp(request);
        JSONObject jsonObject = userCenterService.getCheckCode(mobile, ip, "1790346");
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");*/
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping("invite/record/{user_id}")
    public ModelAndView getInviteRecord(@PathVariable(value = "user_id") String userId, String mobile){
        JSONObject inviteRecord = userCenterService.getInviteRecord(userId, 1, 6);
        inviteRecord.put("mobile", mobile);
        return new ModelAndView("helen_activity/helen_act_sc_joinSuc_record", inviteRecord);
    }

    @ResponseBody
    @RequestMapping("land/{user_phone}")
    public ModelAndView land(@PathVariable(value = "user_phone") String userPhone){
        Map<String, Object> map = new HashMap<>();
        map.put("userPhone", userPhone);
        return new ModelAndView("helen_activity/helen_act_sc_land", map);
    }

    @RequestMapping("register/success/{mobile}")
    public ModelAndView registerSuccess(@PathVariable(value = "mobile") String mobile){
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        return new ModelAndView("helen_activity/helen_act_sc_joinSuc", map);
    }
}

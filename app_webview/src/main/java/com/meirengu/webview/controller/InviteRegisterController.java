package com.meirengu.webview.controller;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.utils.RequestUtil;
import com.meirengu.webview.service.InviteRegisterService;
import com.meirengu.webview.utils.AESOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 邀请注册控制类
 *
 * @author Marvin
 * @create 2017-03-31 下午4:36
 */
@Controller
public class InviteRegisterController {

    @Autowired
    InviteRegisterService inviteRegisterService;

    private static final Logger logger  = LoggerFactory.getLogger(InviteRegisterController.class);

    @RequestMapping(value = "invite/")
    public String invite(){
        return "invite_register";
    }

    @RequestMapping(value = "invite/{inviter_phone}")
    public ModelAndView invite(@PathVariable(value = "inviter_phone") String inviterPhone){

        try {
            inviterPhone = AESOperator.getInstance().decrypt(inviterPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("inviteRegister params  >> inviterPhone:{} ", inviterPhone);
        Map model = new HashMap();
        model.put("inviterPhone", inviterPhone);
        return new ModelAndView("invite_register", model);
    }

    @RequestMapping(value = "invite/register")
    public ModelAndView inviteRegister(@RequestParam(value = "register_phone") String registerPhone, @RequestParam(value = "inviter_phone") String inviterPhone, HttpServletRequest request){
        JSONObject jsonObject = inviteRegisterService.inviteRegister(registerPhone,inviterPhone, "2", RequestUtil.getClientIp(request));
        Map model = new HashMap();
        model.put("jsonObject", jsonObject);
        return new ModelAndView("invite_register", model);
    }

}

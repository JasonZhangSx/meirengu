package com.meirengu.webview.controller;

import com.meirengu.controller.BaseController;
import com.meirengu.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author 建新
 * @create 2017-05-27 11:53
 */
@Controller
@RequestMapping("uc")
public class UserCenterController extends BaseController{

    Logger logger = LoggerFactory.getLogger(UserCenterController.class);

    @ResponseBody
    @RequestMapping("login")
    public Map<String, Object> login(String telphone, String code){


        return null;
    }


    @ResponseBody
    @RequestMapping("login/code")
    public Map<String, Object> getLoginCode(String mobile, HttpServletRequest request){
        String ip = RequestUtil.getClientIp(request);

        return null;
    }



}

package com.meirengu.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-01-19 11:04
 */
@Controller
@RequestMapping
public class LoginController extends BaseController{

    @RequestMapping("to-login")
    public String toLogin(){
        return "index";
    }
}

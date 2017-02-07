package com.meirengu.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Index控制器
 *
 * @author Marvin
 * @create 2017-02-07 下午5:17
 */
@Controller
public class IndexController {

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }


}

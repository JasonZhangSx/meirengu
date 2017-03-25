package com.meirengu.erp.controller.crowdfunding;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-25 12:33
 */
@RestController
public class IndexController {

    @RequestMapping("index")
    public ModelAndView toIndex(){

        return new ModelAndView("/index");
    }
}

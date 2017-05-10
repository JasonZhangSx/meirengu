package com.meirengu.webview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 项目控制层
 * @author 建新
 * @create 2017-05-09 18:51
 */
@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @RequestMapping(value = "item/{item_id}")
    public ModelAndView itemDetail(@PathVariable(value = "item_id")Integer itemId){

        return new ModelAndView("item_detail");
    }
}

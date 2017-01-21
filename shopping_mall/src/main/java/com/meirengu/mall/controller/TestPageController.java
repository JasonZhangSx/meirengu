package com.meirengu.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-01-21 23:57
 */
@Controller
@RequestMapping("to")
public class TestPageController extends BaseController{

    @RequestMapping(value="recommend-add")
    public String toRecommendAdd(){
        return "recommendAdd";
    }

    @RequestMapping(value="case-add")
    public String toCaseAdd(){
        return "caseAdd";
    }
}

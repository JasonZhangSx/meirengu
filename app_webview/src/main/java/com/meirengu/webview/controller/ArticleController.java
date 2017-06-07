package com.meirengu.webview.controller;

import com.meirengu.webview.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 文章控制层
 * @author 建新
 * @create 2017-06-07 11:28
 */
@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping("query")
    public ModelAndView query(int page, int perPage, int acId){
        Map<String, Object> articleMap = (Map<String, Object>) articleService.query(page, perPage, true, acId);
        return new ModelAndView("article_list", articleMap);
    }

}

package com.meirengu.news.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.news.common.StatusCode;
import com.meirengu.news.model.Article;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建新 on 2016/12/27.
 */
@Controller
public class ArticleController extends BaseController{

    @Autowired
    ArticleService articleService;

/*
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
*/

    /**
     * 推荐列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/article/recommend/list", method = {RequestMethod.POST})
    public Map<String, Object> recommendList(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                                    @RequestParam(value = "sortby", required = false) String sortBy,
                                    @RequestParam(value = "order", required = false) String order,
                                    @RequestParam(value = "search_text", required = false) String searchText){

        Map paramMap = new HashMap<String, Object>();
        Page<Article> page = super.setPageParams(pageSize,pageNum);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        paramMap.put("searchText", searchText);
        paramMap.put("isCommend", 1);
        paramMap.put("position", 0);
        paramMap.put("isPublish", 1);
        page = articleService.getPageList(page, paramMap);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("position0", JSON.toJSON(page));
        int i = 1;
        while(i < 3){
            paramMap.put("position", i);
            List<Map<String, Object>> list = articleService.getList(paramMap);
            returnMap.put("position"+i, JSON.toJSON(list));
            i++;
        }
        return super.setReturnMsg(StatusCode.STATUS_0, returnMap, StatusCode.STATUS_0_MSG);
    }

    /**
     * 获取文章列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/article/list", method = {RequestMethod.POST})
    public Map<String, Object> list(@RequestParam(value = "ac_id", required = false, defaultValue = "0") int acId,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                                    @RequestParam(value = "sortby", required = false) String sortBy,
                                    @RequestParam(value = "order", required = false) String order,
                                    @RequestParam(value = "search_text", required = false) String searchText,
                                    @RequestParam(value = "is_commend", required = false, defaultValue = "0") int isCommend){

        Map paramMap = new HashMap<String, Object>();
        Page<Article> page = super.setPageParams(pageSize,pageNum);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        paramMap.put("searchText", searchText);
        paramMap.put("acId", acId);
        paramMap.put("isCommend", isCommend);
        paramMap.put("isPublish", 1);
        page = articleService.getPageList(page, paramMap);

        return super.setReturnMsg(StatusCode.STATUS_0, page, StatusCode.STATUS_0_MSG);
    }

    /**
     * 文章新增
     * @param acId
     * @param url
     * @param show
     * @param sort
     * @param title
     * @param content
     * @return
     */
    @RequestMapping(value = "/article/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insert(@RequestParam(value = "ac_id", required = false) int acId,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "show", required = false) int show,
                                     @RequestParam(value = "sort", required = false) int sort,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "content", required = false) String content){
        Article article = this.packageA(0, acId, url, show, sort, title, content, 1);
        int i = this.articleService.insert(article);
        if(i > 0){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }

    /**
     * 文章删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") int id){
        int i = this.articleService.delete(id);
        if(i > 0){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }

    /**
     * 文章修改
     * @param id
     * @return
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> update(@PathVariable("id") int id,
                                     @RequestParam(value = "ac_id", required = false) int acId,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "show", required = false) int show,
                                     @RequestParam(value = "sort", required = false) int sort,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "content", required = false) String content){
        Article article = this.packageA(id, acId, url, show, sort, title, content, 1);
        int i = this.articleService.insert(article);
        if(i > 0){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> detail(@PathVariable("id") int id){
        Map<String, Object> map = articleService.detail(id);
        return super.setReturnMsg(StatusCode.STATUS_0, map, StatusCode.STATUS_0_MSG);
    }

    private Article packageA(int id, int acId, String url, int show, int sort, String title, String content, int flag){
        Article article = new Article();
        article.setId(id);
        article.setAcId(acId);
        article.setUrl(url);
        article.setShow(show);
        article.setSort(sort);
        article.setTitle(title);
        article.setContent(content);
        article.setFlag(flag);
        return article;
    }
}

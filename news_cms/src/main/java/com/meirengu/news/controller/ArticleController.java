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
@RequestMapping("/article")
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
    @RequestMapping(value = "/recommend/list", method = {RequestMethod.POST})
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
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
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
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insert(@RequestParam(value = "ac_id") int acId,
                                     @RequestParam(value = "url") String url,
                                     @RequestParam(value = "show") int show,
                                     @RequestParam(value = "img") String img,
                                     @RequestParam(value = "sort") int sort,
                                     @RequestParam(value = "title") String title,
                                     @RequestParam(value = "content") String content,
                                     @RequestParam(value = "is_banner") int isBanner,
                                     @RequestParam(value = "is_commend") int isCommend,
                                     @RequestParam(value = "is_publish") int isPublish){
        Article article = this.packageA(0, acId, url, show, sort, title, content, 1, img, isBanner, isCommend, isPublish);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> update(@PathVariable("id") int id,
                                     @RequestParam(value = "ac_id") int acId,
                                     @RequestParam(value = "url") String url,
                                     @RequestParam(value = "show") int show,
                                     @RequestParam(value = "sort") int sort,
                                     @RequestParam(value = "img") String img,
                                     @RequestParam(value = "title") String title,
                                     @RequestParam(value = "content") String content,
                                     @RequestParam(value = "is_banner") int isBanner,
                                     @RequestParam(value = "is_commend") int isCommend,
                                     @RequestParam(value = "is_publish") int isPublish){
        Article article = this.packageA(id, acId, url, show, sort, title, content, 1, img, isBanner, isCommend, isPublish);
        int i = this.articleService.update(article);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> detail(@PathVariable("id") int id){
        Map<String, Object> map = articleService.detail(id);
        return super.setReturnMsg(StatusCode.STATUS_0, map, StatusCode.STATUS_0_MSG);
    }

    /**
     * 发布文章
     * @param id
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> publish(@RequestParam(value = "id") int id, @RequestParam(value = "is_publish") int isPublish){
        if(articleService.publish(id, isPublish)){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        }else{
            return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
        }
    }

    private Article packageA(int id, int acId, String url, int show, int sort,
                             String title, String content, int flag, String img,
                             int isBanner, int isCommend, int isPublish){
        Article article = new Article();
        article.setId(id);
        article.setAcId(acId);
        article.setUrl(url);
        article.setShow(show);
        article.setSort(sort);
        article.setTitle(title);
        article.setContent(content);
        article.setFlag(flag);
        article.setImg(img);
        article.setIsBanner(isBanner);
        article.setIsCommend(isCommend);
        article.setIsPublish(isPublish);
        return article;
    }
}

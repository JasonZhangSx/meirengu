package com.meirengu.news.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.news.common.StatusCode;
import com.meirengu.news.model.Article;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.ArticleService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 * Created by 建新 on 2016/12/27.
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;

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
        try{
            page = articleService.getPageList(page, paramMap);
            if(page.getList().size() != 0){
                return super.setReturnMsg(StatusCode.STATUS_200, page, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_501, page, StatusCode.STATUS_501_MSG);
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
        }
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
    public Map<String,Object> insert(@RequestParam(value = "ac_id", required = false) Integer acId,
                                     @RequestParam(value = "url", required = false) String url,
                                     @RequestParam(value = "show",required = false) Integer show,
                                     @RequestParam(value = "img", required = false) String img,
                                     @RequestParam(value = "sort", required = false) Integer sort,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "content", required = false) String content,
                                     @RequestParam(value = "is_banner", required = false) Integer isBanner,
                                     @RequestParam(value = "is_commend", required = false) Integer isCommend,
                                     @RequestParam(value = "is_publish", required = false) Integer isPublish){

        if(null == acId){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "ac_id"));
        }

        if(null == url){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "url"));
        }

        if(null == show){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "show"));
        }

        if(null == img){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "img"));
        }

        if(null == sort){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "sort"));
        }

        if(null == title){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "title"));
        }

        if(null == content){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "content"));
        }

        if(null == isBanner){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_banner"));
        }

        if(null == isCommend){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_commend"));
        }

        if(null == isPublish){
            return super.setReturnMsg(StatusCode.STATUS_4210, null, String.format(StatusCode.STATUS_4210_MSG, "is_publish"));
        }

        Article article = this.packageA(0, acId, url, show, sort, title, content, 1, img, isBanner, isCommend, isPublish);
        try{
            int i = this.articleService.insert(article);
            if(i > 0){
                return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
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
        try{
            int i = this.articleService.delete(id);
            if(i > 0){
                return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
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
        try{
            int i = this.articleService.update(article);
            if(i > 0){
                return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }
        }catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
        }
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> detail(@PathVariable("id") String id){
        try{
            Map<String, Object> map = articleService.detail(Integer.parseInt(id));
            if(null != map){
                return super.setReturnMsg(StatusCode.STATUS_200, map, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_501, map, StatusCode.STATUS_501_MSG);
            }
        }catch (ClassCastException e){
            LOGGER.error("class cast exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
        }catch (Exception e){
            LOGGER.error("other exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
        }
    }

    /**
     * 发布文章
     * @param id
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> publish(@RequestParam(value = "id") int id, @RequestParam(value = "is_publish") int isPublish){
        try {
            if(articleService.publish(id, isPublish)){
                return super.setReturnMsg(StatusCode.STATUS_200, null, StatusCode.STATUS_200_MSG);
            }else{
                return super.setReturnMsg(StatusCode.STATUS_500, null, StatusCode.STATUS_500_MSG);
            }
        } catch (Exception e){
            LOGGER.error("throw exception:", e);
            return super.setReturnMsg(StatusCode.STATUS_400, null, e.getMessage());
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

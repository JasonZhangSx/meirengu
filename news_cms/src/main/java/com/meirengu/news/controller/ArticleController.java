package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.news.model.Article;
import com.meirengu.news.service.ArticleService;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章控制层
 * @author 建新
 * @create 2017-05-08 14:21
 */
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;

    /**
     * 获取请求列表
     * @param pageSize
     * @param pageNum
     * @param isPage
     * @param sortBy
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Result list(@RequestParam(value = "per_page", required = false, defaultValue = "10") int pageSize,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "is_page", required = false) boolean isPage,
                       @RequestParam(value = "flag", required = false) Integer flag,
                       @RequestParam(value = "ac_id", required = false) Integer acId,
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sortBy", sortBy);
        map.put("order", order);
        map.put("acId", acId);
        map.put("flag", flag);

        if(isPage){
            Page<Article> page = new Page<Article>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = articleService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = articleService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 插入文章
     * @param acId
     * @param articleUrl
     * @param articleLabel
     * @param articleShow
     * @param articleSort
     * @param articleImg
     * @param articleTitle
     * @param articleContent
     * @param articleIsBanner
     * @param articleIsCommend
     * @param articleIsPublish
     * @param flag
     * @param createUser
     * @param createUserName
     * @param createUserImg
     * @param articleTime
     * @param createUserType
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "ac_id", required = false) Integer acId,
                         @RequestParam(value = "article_url", required = false) String articleUrl,
                         @RequestParam(value = "article_label", required = false) String articleLabel,
                         @RequestParam(value = "article_show", required = false) Integer articleShow,
                         @RequestParam(value = "article_sort", required = false) Integer articleSort,
                         @RequestParam(value = "article_img", required = false) String articleImg,
                         @RequestParam(value = "article_title", required = false) String articleTitle,
                         @RequestParam(value = "article_content", required = false) String articleContent,
                         @RequestParam(value = "article_is_banner", required = false) Integer articleIsBanner,
                         @RequestParam(value = "article_is_commend", required = false) Integer articleIsCommend,
                         @RequestParam(value = "article_is_publish", required = false) Integer articleIsPublish,
                         @RequestParam(value = "flag", required = false) Integer flag,
                         @RequestParam(value = "create_user", required = false) Integer createUser,
                         @RequestParam(value = "create_user_name", required = false) String createUserName,
                         @RequestParam(value = "create_user_img", required = false) String createUserImg,
                         @RequestParam(value = "article_time", required = false) Date articleTime,
                         @RequestParam(value = "create_user_type", required = false) Integer createUserType){

        Article article = setEntity(null, acId, articleUrl, articleLabel, articleShow,
                    articleSort, articleImg, articleTitle, articleContent,
                    articleIsBanner, articleIsCommend, articleIsPublish, articleTime,
                    flag, createUser, createUserName, createUserImg,
                    createUserType, new Date());
        try {
            int insertNum = articleService.insert(article);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert article throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(value = "id", required = false)Integer id){
        try {
            Article article = articleService.detail(id);
            return super.setResult(StatusCode.OK, article, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get article throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改文章
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "article_id", required = false) Integer articleId,
                         @RequestParam(value = "ac_id", required = false) Integer acId,
                         @RequestParam(value = "article_url", required = false) String articleUrl,
                         @RequestParam(value = "article_label", required = false) String articleLabel,
                         @RequestParam(value = "article_show", required = false) Integer articleShow,
                         @RequestParam(value = "article_sort", required = false) Integer articleSort,
                         @RequestParam(value = "article_img", required = false) String articleImg,
                         @RequestParam(value = "article_title", required = false) String articleTitle,
                         @RequestParam(value = "article_content", required = false) String articleContent,
                         @RequestParam(value = "article_is_banner", required = false) Integer articleIsBanner,
                         @RequestParam(value = "article_is_commend", required = false) Integer articleIsCommend,
                         @RequestParam(value = "article_is_publish", required = false) Integer articleIsPublish,
                         @RequestParam(value = "flag", required = false) Integer flag,
                         @RequestParam(value = "create_user", required = false) Integer createUser,
                         @RequestParam(value = "create_user_name", required = false) String createUserName,
                         @RequestParam(value = "create_user_img", required = false) String createUserImg,
                         @RequestParam(value = "article_time", required = false) Date articleTime,
                         @RequestParam(value = "create_user_type", required = false) Integer createUserType){

        Article article = setEntity(articleId, acId, articleUrl, articleLabel, articleShow,
                articleSort, articleImg, articleTitle, articleContent,
                articleIsBanner, articleIsCommend, articleIsPublish, articleTime,
                flag, createUser, createUserName, createUserImg,
                createUserType, new Date());
        try {
            int updateNum = articleService.update(article);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update article throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /*
     * 删除文章
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam(value = "id", required = false)Integer id){
        try {
            int deleteNum = articleService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ALREADY_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete article detail throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private Article setEntity(Integer articleId, Integer acId, String articleUrl, String articleLabel, Integer articleShow,
                              Integer articleSort, String articleImg, String articleTitle, String articleContent, Integer
                                      articleIsBanner, Integer articleIsCommend, Integer articleIsPublish, Date articleTime,
                              Integer flag, Integer createUser, String createUserName, String createUserImg, Integer
                                      createUserType, Date createTime) {
        Article article = new Article();
        article.setArticleId(articleId);
        article.setAcId(acId);
        article.setArticleUrl(articleUrl == null ? "" : articleUrl);
        article.setArticleImg(articleImg == null ? "" : articleImg);
        article.setArticleLabel(articleLabel == null ? "" : articleLabel);
        article.setArticleShow(articleShow);
        article.setArticleSort(articleSort);
        article.setArticleTitle(articleTitle == null ? "" : articleTitle);
        article.setArticleContent(articleContent == null ? "" : articleContent);
        article.setArticleIsBanner(articleIsBanner);
        article.setArticleIsCommend(articleIsCommend);
        article.setArticleIsPublish(articleIsPublish);
        article.setArticleTime(articleTime);
        article.setFlag(flag);
        article.setCreateUser(createUser);
        article.setCreateUserName(createUserName == null ? "" : createUserName);
        article.setCreateUserImg(createUserImg == null ? "" : createUserImg);
        article.setCreateUserType(createUserType);
        article.setCreateTime(createTime);

        return article;
    }
}

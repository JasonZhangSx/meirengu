package com.meirengu.news.controller;

import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.news.model.ArticleClass;
import com.meirengu.news.service.ArticleClassService;
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
 * Created by 建新 on 2016/12/27.
 */
@RequestMapping("/class")
@Controller
public class ArticleClassController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleClassController.class);

    @Autowired
    ArticleClassService articleClassService;

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
                       @RequestParam(value = "sortby", required = false, defaultValue = "create_time") String sortBy,
                       @RequestParam(value = "order", required = false, defaultValue = "desc") String order){

        //默认不分页
        if(StringUtil.isEmpty(isPage)){
            isPage = false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sortBy", sortBy);
        map.put("order", order);

        if(isPage){
            Page<ArticleClass> page = new Page<ArticleClass>();
            page.setPageNow(pageNum);
            page.setPageSize(pageSize);
            page = articleClassService.getListByPage(page, map);
            return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
        }else {
            List<Map<String, Object>> list = articleClassService.getList(map);
            return super.setResult(StatusCode.OK, list, StatusCode.codeMsgMap.get(StatusCode.OK));
        }
    }

    /**
     * 插入文章分类
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "ac_code", required = false) String acCode,
                         @RequestParam(value = "ac_name", required = false) String acName,
                         @RequestParam(value = "ac_parent_id", required = false) Integer acParentId,
                         @RequestParam(value = "ac_sort", required = false) Integer acSort,
                         @RequestParam(value = "flag", required = false) Integer flag){

        ArticleClass articleClass = setEntity(null, acCode, acName, acParentId, acSort, flag, new Date());
        try {
            int insertNum = articleClassService.insert(articleClass);
            if(insertNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(com.meirengu.common.StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_INSERT, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_INSERT));
            }
        }catch (Exception e){
            LOGGER.error(">> insert article class throw exception: {}", e);
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
            ArticleClass articleClass = articleClassService.detail(id);
            return super.setResult(StatusCode.OK, articleClass, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            LOGGER.error(">> get article class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 修改文章
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "ac_id", required = false) Integer acId,
                         @RequestParam(value = "ac_code", required = false) String acCode,
                         @RequestParam(value = "ac_name", required = false) String acName,
                         @RequestParam(value = "ac_parent_id", required = false) Integer acParentId,
                         @RequestParam(value = "ac_sort", required = false) Integer acSort,
                         @RequestParam(value = "flag", required = false) Integer flag){

        ArticleClass articleClass = setEntity(acId, acCode, acName, acParentId, acSort, flag, null);
        try {
            int updateNum = articleClassService.update(articleClass);
            if(updateNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ERROR_UPDATE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ERROR_UPDATE));
            }
        }catch (Exception e){
            LOGGER.error(">> update article class throw exception: {}", e);
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
            int deleteNum = articleClassService.delete(id);
            if(deleteNum == 1){
                return super.setResult(StatusCode.OK, "", StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                return super.setResult(StatusCode.PARTNER_CLASS_ALREADY_DELETE, "", StatusCode.codeMsgMap.get(StatusCode.PARTNER_CLASS_ALREADY_DELETE));
            }
        }catch (Exception e){
            LOGGER.error(">> delete article class throw exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, "", StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private ArticleClass setEntity(Integer acId, String acCode, String acName, Integer acParentId, Integer acSort, Integer flag,
                              Date createTime) {
        ArticleClass articleClass = new ArticleClass();

        articleClass.setAcId(acId);
        articleClass.setAcCode(acCode == null ? "" : acCode);
        articleClass.setAcName(acName == null ? "" : acName);
        articleClass.setAcParentId(acParentId);
        articleClass.setAcSort(acSort);
        articleClass.setFlag(flag);
        articleClass.setCreateTime(createTime);
        return articleClass;
    }
}

package com.meirengu.news.controller;

import com.meirengu.news.common.Constants;
import com.meirengu.news.common.StatusCode;
import com.meirengu.news.model.ArticleClass;
import com.meirengu.news.model.Page;
import com.meirengu.news.service.ArticleClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private ArticleClassService articleClassService;

    @ResponseBody
    @RequestMapping(value = "test")
    public String test(){
        return "test";
    }
    /**
     *
     * @param searchText 搜索框内容
     * @param pageNum 当前页
     * @param pageSize 每页显示的条数
     * @param sortBy 排序字段
     * @param order 升序/降序
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.POST})
    public Map<String, Object> list(@RequestParam(value="search_text", required = false) String searchText,
                                    @RequestParam(value="page", required = false, defaultValue = "1") int pageNum,
                                    @RequestParam(value="per_page", required = false, defaultValue = "10") int pageSize,
                                    @RequestParam(value="sortby", required = false) String sortBy,
                                    @RequestParam(value="order", required = false) String order){
        Map paramMap = new HashMap<String, Object>();
        Page<ArticleClass> page = super.setPageParams(pageSize,pageNum);
        paramMap.put("sortBy", sortBy);
        paramMap.put("order", order);
        paramMap.put("searchText", searchText);
        page = articleClassService.getPageList(page, paramMap);

        return super.setReturnMsg(StatusCode.STATUS_0, page, StatusCode.STATUS_0_MSG);
    }

    /**
     * 文章分类新增
     * @param code
     * @param name
     * @param parentId
     * @param sort
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insert(@RequestParam("code") String code,
                                     @RequestParam("name") String name,
                                     @RequestParam("parent_id") int parentId,
                                     @RequestParam("sort") String sort){

        ArticleClass ac = this.packageAc(0, code, name, parentId, Constants.DEL_FLAG_FALSE);
        //先判断code和name是否有重复
        List<ArticleClass> acList = articleClassService.getByCodeOrName(ac);
        if(acList.size() > 0){
            return super.setReturnMsg(StatusCode.STATUS_500, null, "code或name已存在！");
        }
        int i = articleClassService.insert(ac);
        if(i > 0){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        } else {
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
        ArticleClass ac = articleClassService.detail(id);
        if(null == ac){
            return super.setReturnMsg(StatusCode.STATUS_500, null, "该项不存在");
        }
        int i = articleClassService.delete(id);
        if(i > 0){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        } else {
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
                                     @RequestParam(value="code", required = false) String code,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "parent_id") int parentId,
                                     @RequestParam(value = "sort", required = false) String sort){
        ArticleClass ac = this.packageAc(id, code, name, parentId, Constants.DEL_FLAG_FALSE);
        int i = articleClassService.update(ac);
        if(i > 0){
            return super.setReturnMsg(StatusCode.STATUS_0, null, StatusCode.STATUS_0_MSG);
        } else {
            return super.setReturnMsg(StatusCode.STATUS_500, null, "该项不存在");
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
        ArticleClass ac = articleClassService.detail(id);
        LOGGER.info("ac is null? " + (ac==null));
        return super.setReturnMsg(StatusCode.STATUS_0, ac, StatusCode.STATUS_0_MSG);
    }


    public ArticleClass packageAc(int id, String code, String name, int parentId, int flag){
        ArticleClass ac = new ArticleClass();
        ac.setId(id);
        ac.setCode(code);
        ac.setName(name);
        ac.setParentId(parentId);
        ac.setFlag(flag);
        return ac;
    }

}

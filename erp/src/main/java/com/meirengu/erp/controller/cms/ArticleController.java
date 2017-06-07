package com.meirengu.erp.controller.cms;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.Article;
import com.meirengu.erp.service.ArticleClassService;
import com.meirengu.erp.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章控制层
 * @author 建新
 * @create 2017-06-05 17:09
 */
@RequestMapping("article")
@Controller
public class ArticleController extends BaseController{


    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleClassService articleClassService;

    /**
     * 跳转到文章信息列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        Map<String, Object> map = new HashMap<>();
        List classList = (List) articleClassService.query(0, 0, false);
        map.put("classList", classList);
        return new ModelAndView("cms/articleList", map);
    }

    /**
     * 文章信息列表数据请求
     * @param input
     * @return
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesOutput query(@Valid DataTablesInput input) throws IOException {

        List<Map<String,Object>> list = null;
        int totalCount = 0;
        int start = input.getStart();
        int length = input.getLength();
        int page = start / length + 1;
        try {
            Map<String, Object> map = articleService.query(page, length, true);
            list = (List<Map<String,Object>>) map.get("list");
            totalCount = Integer.parseInt(map.get("totalCount").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throw exception:{}", e);
        }
        return setDataTablesOutput(input, list, totalCount);
    }

    /**
     * 跳转到文章详情页
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(Integer id) {
        if(id == null){
            Map<String, Object>  map = new HashMap<String, Object>();
            List classList = (List) articleClassService.query(0, 0, false);
            map.put("classList", classList);
            return new ModelAndView("cms/articleDetail", map);
        }else {
            Map<String, Object>  map = articleService.detail(id);
            List classList = (List) articleClassService.query(0, 0, false);
            map.put("classList", classList);
            return new ModelAndView("cms/articleDetail", map);
        }
    }

    /**
     * 保存文章信息（新增和更新）
     * @param article
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(Article article) {
        Map<String, Object> map ;
        //id为空 新增
        if(article.getArticleId() == null || article.getArticleId() == 0){
            map = articleService.add(article);
        }else {
            map = articleService.update(article);
        }
        return map;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = articleService.delete(id);
        return map;
    }
}

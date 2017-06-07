package com.meirengu.erp.controller.cms;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.Article;
import com.meirengu.erp.model.ArticleClass;
import com.meirengu.erp.service.ArticleClassService;
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
import java.util.List;
import java.util.Map;

/**
 * 文章分类
 * @author 建新
 * @create 2017-06-05 17:09
 */
@Controller
@RequestMapping("article/class")
public class ArticleClassController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ArticleClassController.class);

    @Autowired
    ArticleClassService articleClassService;

    /**
     * 跳转到文章信息列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view() {
        return "cms/articleClassList";
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
            Map<String, Object> map = (Map<String, Object>) articleClassService.query(page, length, true);
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
            return new ModelAndView("cms/articleClassDetail");
        }else {
            Map<String, Object>  map = articleClassService.detail(id);
            return new ModelAndView("cms/articleClassDetail", map);
        }
    }

    /**
     * 保存文章信息（新增和更新）
     * @param articleClass
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(ArticleClass articleClass) {
        Map<String, Object> map ;
        //id为空 新增
        if(articleClass.getAcId() == null || articleClass.getAcId() == 0){
            map = articleClassService.add(articleClass);
        }else {
            map = articleClassService.update(articleClass);
        }
        return map;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = articleClassService.delete(id);
        return map;
    }
}

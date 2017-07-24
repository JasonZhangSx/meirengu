package com.meirengu.erp.controller.cms;

import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.model.IpWhiteList;
import com.meirengu.erp.service.IpWhiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
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
 * IP白名单控制层
 * @author 建新
 * @create 2017-07-24 11:47
 */
@Controller
@RequestMapping("ip_white")
public class IpWhiteController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(IpWhiteController.class);

    @Autowired
    IpWhiteService ipWhiteService;

    /**
     * 跳转到文章信息列表页面
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view() {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView("cms/ipWhiteList", map);
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
            Map<String, Object> map = ipWhiteService.query(page, length, true, null);
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
            return new ModelAndView("cms/ipWhiteDetail", map);
        }else {
            Map<String, Object>  map = ipWhiteService.detail(id);
            return new ModelAndView("cms/ipWhiteDetail", map);
        }
    }

    /**
     * 保存文章信息（新增和更新）
     * @param ipWhiteList
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(IpWhiteList ipWhiteList) {
        Map<String, Object> map ;
        //id为空 新增
        if(ipWhiteList.getId() == null || ipWhiteList.getId() == 0){
            map = ipWhiteService.add(ipWhiteList);
        }else {
            map = ipWhiteService.update(ipWhiteList);
        }
        return map;
    }

    @RequestMapping(value = "/cache/set", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setCache() {
        Map<String, Object> map = ipWhiteService.setCache();
        return map;
    }

}

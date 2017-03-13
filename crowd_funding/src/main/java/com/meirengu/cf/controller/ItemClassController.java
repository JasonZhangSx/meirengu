package com.meirengu.cf.controller;

import com.meirengu.cf.model.ItemClass;
import com.meirengu.cf.service.ItemClassService;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-13 11:32
 */
@Controller
@RequestMapping("item_class")
public class ItemClassController extends BaseController{

    @Autowired
    ItemClassService itemClassService;

    @RequestMapping("list")
    @ResponseBody
    public Result list(HttpServletRequest request){
        int pageNum = 1;
        int pageSize = 10;
        if(!StringUtil.isEmpty(request.getParameter("page"))){
            pageNum = Integer.parseInt(request.getParameter("page"));
        }

        if(!StringUtil.isEmpty(request.getParameter("per_page"))){
            pageSize = Integer.parseInt(request.getParameter("per_page"));
        }

        Map<String, Object> map = new HashMap<>();
        Page<ItemClass> page = new Page<>();
        page.setPageNow(pageNum);
        page.setPageSize(pageSize);
        page = itemClassService.getListByPage(page, map);

        return super.setResult(StatusCode.OK, page, StatusCode.codeMsgMap.get(StatusCode.OK));
    }
}

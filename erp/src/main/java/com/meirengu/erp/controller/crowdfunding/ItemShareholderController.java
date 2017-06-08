package com.meirengu.erp.controller.crowdfunding;

import com.meirengu.controller.BaseController;
import com.meirengu.erp.model.ItemShareholder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 项目股东控制层
 * @author 建新
 * @create 2017-06-08 18:42
 */
@RequestMapping("shareholder")
@Controller
public class ItemShareholderController extends BaseController{

    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> query(){

        return null;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save(ItemShareholder shareholder){
        return null;
    }
}

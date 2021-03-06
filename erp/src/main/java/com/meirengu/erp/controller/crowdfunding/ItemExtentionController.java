package com.meirengu.erp.controller.crowdfunding;

import com.meirengu.controller.BaseController;
import com.meirengu.erp.model.ItemExtention;
import com.meirengu.erp.service.ItemExtentionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 项目扩展信息控制层
 * @author 建新
 * @create 2017-06-08 18:13
 */
@Controller
@RequestMapping("item/extention")
public class ItemExtentionController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ItemExtentionController.class);

    @Autowired
    ItemExtentionService itemExtentionService;

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save(ItemExtention itemExtention){
        Map<String, Object> detailMap = itemExtentionService.detail(itemExtention.getItemId());
        Map<String, Object> map;
        if(detailMap == null){
            map = itemExtentionService.add(itemExtention);
        }else{
            map = itemExtentionService.update(itemExtention);
        }
        return map;
    }


    @RequestMapping("detail")
    @ResponseBody
    public Map<String, Object> detail(Integer itemId){
        Map<String, Object> detailMap = itemExtentionService.detail(itemId);
        return detailMap;
    }


}

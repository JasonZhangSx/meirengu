package com.meirengu.webview.controller;

import com.meirengu.webview.service.ItemService;
import com.meirengu.webview.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目控制层
 * @author 建新
 * @create 2017-05-09 18:51
 */
@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "item/{item_id}")
    public ModelAndView itemDetail(@PathVariable(value = "item_id")Integer itemId){
        Map<String, Object> model = new HashMap<>();
        Map itemDetail = itemService.getItemDetail(itemId);
        List levelList = itemService.getLevelList(itemId);
        Map orderMap = orderService.getSupportList(itemId);
        List contentList = itemService.getContentList(itemId);
        model.put("itemDetail", itemDetail);
        model.put("levelList", levelList);
        model.put("orderMap", orderMap);
        model.put("contentList", contentList);
        return new ModelAndView("item_detail", model);
    }
}

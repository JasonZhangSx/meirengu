package com.meirengu.webview.controller;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.webview.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台公告控制类
 *
 * @author Marvin
 * @create 2017-03-30 下午7:17
 */
@Controller
public class BulletinController {

    @Autowired
    BulletinService bulletinService;

    @RequestMapping(value = "bulletins", method = RequestMethod.GET)
    public ModelAndView bulletins(){
        JSONObject data = bulletinService.bulletins();
        List list = (List) data.get("list");
        if(list.size() > 0){
            return new ModelAndView("bulletin_list", data);
        }else {
            return new ModelAndView("no_data");
        }
    }

    @RequestMapping(value = "bulletins/{bulletin_id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "bulletin_id") Integer bulletinId){
        Map model = new HashMap();
        model.put("bulletin", bulletinService.bulletinById(bulletinId));
        return new ModelAndView("bulletin_detail", model);
    }

}

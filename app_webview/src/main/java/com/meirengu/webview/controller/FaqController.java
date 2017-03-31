package com.meirengu.webview.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.meirengu.webview.model.FaqClass;
import com.meirengu.webview.service.FaqService;
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
 * FAQ控制类
 *
 * @author Marvin
 * @create 2017-03-30 下午9:27
 */
@Controller
public class FaqController {

    @Autowired
    FaqService faqService;

    @RequestMapping(value = "faqs", method = RequestMethod.GET)
    public ModelAndView faqs(){
        Map model = new HashMap();
        JSON faqClassesJson = faqService.faqClasses();
        model.put("faqClasses", faqClassesJson);

        List<FaqClass> list = JSONArray.parseArray(faqClassesJson.toJSONString(), FaqClass.class);
        if (list != null && list.size() > 0){
            Integer defaultFaqClassId = list.get(0).getClassId();
            JSON classFaqsJson = faqService.faqByClassId(defaultFaqClassId);
            model.put("index", 0);
            model.put("classFaqs", classFaqsJson);
        }
        return new ModelAndView("faqs", model);
    }

    @RequestMapping(value = "faqs/{class_id}/{index}", method = RequestMethod.GET)
    public ModelAndView classFaqs(@PathVariable(value = "class_id") Integer classId, @PathVariable(value = "index") Integer index){
        Map model = new HashMap();
        model.put("faqClasses", faqService.faqClasses());
        model.put("classFaqs", faqService.faqByClassId(classId));
        model.put("index", index);
        return new ModelAndView("faqs", model);
    }


}

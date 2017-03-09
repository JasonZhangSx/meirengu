package com.meirengu.admin.controller;

import com.alibaba.fastjson.JSON;
import com.meirengu.admin.util.FileUtil;
import com.meirengu.admin.util.HttpUtil;
import com.meirengu.admin.util.UtilData;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/1 16:38.
 */
@CrossOrigin
@Controller
@RequestMapping("/service")
public class ServiceController {
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object addDoctorData(String serviceCode,HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        if (null==serviceCode||"".equals(serviceCode)){
            map.put("msg","服务码为空,请正确输入！");
            map.put("code","400");
            return JSON.toJSON(map);
        }
        map.put("code",serviceCode);
        String result = HttpUtil.httpPostForm("http://120.27.37.54/shopping_mall/service_code/validate",map,null);
//        map.clear();
//        map=new Gson().fromJson(result,Map.class);
//        if (200==Integer.valueOf(map.get("code"))){
//            map.put("msg","服务码验证成功");
//        }
//        return JSON.toJSON(map);
        return JSON.parse(result);
    }

    @RequestMapping(value = "saveEditImage",method = RequestMethod.POST)
    @ResponseBody
    public String addHospital(HttpServletRequest request) {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        Iterator<String> iter = new ArrayList<String>().iterator();
        MultipartHttpServletRequest multiRequest = null;
        Map<String,String> map = new HashMap<>();
        //判断 request 是否有文件上传,即多部分请求
        if(resolver.isMultipart(request)){
            //转换成多部分request
            multiRequest=(MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            iter=multiRequest.getFileNames();
        }
        while(iter.hasNext()){
            try {
                try {
                    map= FileUtil.createFile(map,iter,multiRequest, UtilData.editImagePath+"/");
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (String s : map.keySet()){
            return "http://image.meirenguvip.com/edit/"+map.get(s);
        }
        return "";
//        modelAndView.addObject("map",map);
//        modelAndView.setViewName("");
//        return selectionHospital(null);
    }
}

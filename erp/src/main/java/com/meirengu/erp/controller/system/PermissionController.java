package com.meirengu.erp.controller.system;

import com.alibaba.fastjson.JSON;
import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/2 18:21.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getPermissionAll() throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissionList",permissionService.getAllPermission());
        modelAndView.setViewName("/system/permissionList");
        return modelAndView;
    }
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDetail(String id) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView();
        if (id!=null&&id!=""){
            modelAndView.addObject("permission",permissionService.selectById(Long.valueOf(id)));
        }
        modelAndView.addObject("permissionList",permissionService.getAllPermission());
        modelAndView.setViewName("/system/editPermission");
        return modelAndView;
    }

    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.POST)
    @ResponseBody
    public String updateAdd(Permission permission)  {
        if (permission.getId()!=null&&permission.getId()>0){
            permissionService.update(permission);
        }else {
            permissionService.insert(permission);
        }
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("code","200");
        return JSON.toJSON(resultMap).toString();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id)  {
        Map<String,String> resultMap = new HashMap<>();
        if (permissionService.delete(Long.valueOf(id))>0){
            resultMap.put("code","200");
        }else {
            resultMap.put("code","400");
        }
        return JSON.toJSON(resultMap).toString();
    }
}

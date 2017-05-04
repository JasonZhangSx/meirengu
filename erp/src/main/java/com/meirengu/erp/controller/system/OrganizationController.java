package com.meirengu.erp.controller.system;

import com.alibaba.fastjson.JSON;
import com.meirengu.commons.authority.model.Organization;
import com.meirengu.commons.authority.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/3 19:35.
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getOrganizationAll(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("organizationList",organizationService.getAllOrganization(null));
        modelAndView.setViewName("/system/organizationList");
        return modelAndView;
    }
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getOrganizationDetail(String id){
        ModelAndView modelAndView = new ModelAndView();
        Organization organization = new Organization();
        if (id!=null&&id!="") {
            organization.setOrganizationId(Integer.valueOf(id));
            modelAndView.addObject("organization",organizationService.getAllOrganization(organization).get(0));
        }
        modelAndView.setViewName("/system/editOrganization");
        return modelAndView;
    }
    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.POST)
    @ResponseBody
    public String updateAdd(Organization organization)  {
        if (organization.getOrganizationId()!=null&&organization.getOrganizationId()>0){
            organizationService.update(organization);
        }else {
            organization.setCreateTime(new Date());
            organizationService.insert(organization);
        }
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("code","200");
        return JSON.toJSON(resultMap).toString();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id)  {
        organizationService.delete(Integer.valueOf(id));
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("code","200");
        return JSON.toJSON(resultMap).toString();
    }
}

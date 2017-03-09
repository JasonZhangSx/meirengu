package com.meirengu.admin.controller;

import com.meirengu.admin.service.impl.DataSourceContextHolder;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/23 19:17.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 获取所有的角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoleAll(Role role){
        DataSourceContextHolder.setDbType("admin");
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roleList = roleService.findRoleAll(role);
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("/view/system/roleinfo/roleinfo_list");
        return modelAndView;
    }

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoleDetail(Integer id){
        DataSourceContextHolder.setDbType("admin");
        ModelAndView modelAndView = new ModelAndView();
        Role role = roleService.findRoleDetail(id);
        modelAndView.addObject("roleDetail",role);
        modelAndView.setViewName("/view/system/roleinfo/roleinfo_update");
        return modelAndView;
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateRole(Role role){
        DataSourceContextHolder.setDbType("admin");
        ModelAndView modelAndView = new ModelAndView();
        int code = roleService.updateRole(role);
        modelAndView.addObject("code",code);
        modelAndView.setViewName("/view/system/roleinfo/roleinfo_update");
        return modelAndView;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getRoleDetail(Role role){
        DataSourceContextHolder.setDbType("admin");
        ModelAndView modelAndView = new ModelAndView();
        int code = roleService.insertRole(role);
        modelAndView.addObject("code",code);
        modelAndView.setViewName("/view/system/roleinfo/roleinfo_add");
        return modelAndView;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteRole(Integer id){
        DataSourceContextHolder.setDbType("admin");
        ModelAndView modelAndView = new ModelAndView();
        int code = roleService.deleteRole(id);
        modelAndView.addObject("code",code);
        modelAndView.setViewName("/view/system/roleinfo/roleinfo_update");
        return modelAndView;
    }
}

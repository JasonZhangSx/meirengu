package com.meirengu.erp.controller.system;

import com.alibaba.fastjson.JSON;
import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.model.RolePermission;
import com.meirengu.commons.authority.service.RolePermissionService;
import com.meirengu.commons.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/23 19:17.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    /**
     * 获取所有的角色
     * @param name
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoleAll(HttpServletRequest request,String name) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView();
        Role role = new Role();
        if (name!=null&&!name.isEmpty()){
            role.setName(new String((name).getBytes("ISO-8859-1"),"utf8"));
        }
        List<Role> roleList = roleService.findRoleAll(role);
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("/system/roleList");
        return modelAndView;
    }
    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView updateOrAdd(Role role) {
        ModelAndView modelAndView = new ModelAndView("/system/editRole");
        Map<Long,Long> map = new HashMap<>();
        Role r = roleService.findRoleDetail(role.getId()==null ? null:Integer.valueOf(role.getId().toString()));
        List<Permission> permissionList = new ArrayList<>();
        if(r.getPermissionList()!=null&&r.getPermissionList().size()!=0){
            permissionList = r.getPermissionList();
        }
        r.setPermissionList(roleService.findRoleDetail(null).getPermissionList());
        for (Permission permission : permissionList){
            map.put(permission.getId(),permission.getId());
        }
        modelAndView.addObject("role",r);
        modelAndView.addObject("rolePermission",map);
        return modelAndView;
    }
    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.POST)
    @ResponseBody
    public String updateAdd(Role role,HttpServletRequest request)  {
        Map<String,Integer> map = new HashMap<>();
        String[] permission = request.getParameterValues("permissionId");
        if (permission!=null&&permission.length!=0){
            for (String s : permission){
                for (String per : s.split(",")){
                    map.put(per,Integer.valueOf(per));
                }
            }
        }
        if (role!=null&&role.getId()!=null&&role.getId()>0){
            roleService.updateRole(role);
        }else {
            roleService.insertRole(role);
        }
        List<RolePermission> permissionList = new ArrayList<>();
        for (String s : map.keySet()){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(Long.valueOf(map.get(s)));
            permissionList.add(rolePermission);
        }
        if (permissionList.size()>0){
            rolePermissionService.deleteByRoleId(role.getId());
            rolePermissionService.insertRolePermission(permissionList);
        }
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("code","200");
        return JSON.toJSON(resultMap).toString();
    }
    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoleDetail(Integer id){
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
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteRole(Integer id){
        int code = roleService.deleteRole(id);
        rolePermissionService.deleteByRoleId(Long.valueOf(id));
        Map<String,String> map = new HashMap<>();
        if (code!=1){
            map.put("code","400");
        }else {
            map.put("code","200");
        }
        return JSON.toJSON(map).toString();
    }
}

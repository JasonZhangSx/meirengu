package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.PermissionMapper;
import com.meirengu.commons.authority.dao.RoleMapper;
import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/16 17:13.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    /**
     * 根据用户获得角色
     * @param accountId
     * @return
     */
    @Override
    public List<Role> findRole(Integer accountId) {
        return roleMapper.findRoleList(accountId);
    }
    /**
     * 根据条件获得角色
     * @param role
     * @return
     */
    @Override
    public List<Role> findRoleAll(Role role) {
        return roleMapper.findRoleAll(role);
    }
    /**
     * 根据角色id查询角色详情
     * @param id
     * @return
     */
    @Override
    public Role findRoleDetail(Integer id) {
        List<Role> list = new ArrayList<>();
        Role role = new Role();
        if (id!=null&&id>0){
            role = roleMapper.findRoleDetail(id);
        }
        if (role.getId()!=null){
            list.add(role);
            role.setPermissionList(permissionMapper.findPermission(list));
        }else {
            role.setPermissionList(tree(permissionMapper.getAllPermission(),0));
        }
        return role;
    }
    /**
     * 根据角色id删除角色记录
     * @param id
     * @return
     */
    @Override
    public int deleteRole(Integer id) {
        return roleMapper.deleteRole(id);
    }
    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public int insertRole(Role role) {
        return roleMapper.insertRole(role);
    }
    /**
     * 更新角色
     * @param role
     * @return
     */
    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }
    /**
     * 递归遍历树形
     * @param list 查询结果
     * @param icParentId 父节点
     * @return 树形结构
     */
    public List<Permission> tree(List<Permission> list, int icParentId){
        List<Permission> permissionList = new ArrayList<>();
        for (Permission vo : list){
            if (vo.getParentId()==icParentId){
                List<Permission> voList=tree(list,vo.getId().intValue());
                vo.setList(voList);
                permissionList.add(vo);
            }
        }
        return permissionList;
    }
}

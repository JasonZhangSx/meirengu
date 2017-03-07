package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.RoleMapper;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/16 17:13.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
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
        return roleMapper.findRoleDetail(id);
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

}

package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.RolePermissionMapper;
import com.meirengu.commons.authority.model.RolePermission;
import com.meirengu.commons.authority.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/28 18:08.
 */
@Service
public class RolePermissionImpl implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public int insertRolePermission(List<RolePermission> list) {
        return rolePermissionMapper.insertList(list);
    }
    @Override
    public int deleteByRoleId(Long roleId) {
        return rolePermissionMapper.deleteByRoleId(roleId);
    }
}

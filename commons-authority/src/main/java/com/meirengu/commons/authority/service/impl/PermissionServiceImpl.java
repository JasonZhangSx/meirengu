package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.PermissionMapper;
import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/16 17:30.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermission(List<Role> roleList) {
        return permissionMapper.findPermission(roleList);
    }
    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }
}

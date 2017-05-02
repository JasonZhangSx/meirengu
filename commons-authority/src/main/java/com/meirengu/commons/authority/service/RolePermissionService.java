package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.RolePermission;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/28 18:06.
 */
public interface RolePermissionService {
    int insertRolePermission(List<RolePermission> list);

    int deleteByRoleId(Long roleId);
}

package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/16 17:28.
 */
public interface PermissionService {
    List<Permission> findPermission(List<Role> roleList);
    List<Permission> getAllPermission();
}

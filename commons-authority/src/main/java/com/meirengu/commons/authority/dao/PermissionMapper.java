package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;

import java.util.List;

public interface PermissionMapper {

    List<Permission> findPermission(List<Role> roleList);

    List<Permission> getAllPermission();

    Permission selectById(Long id);

    int insert(Permission permission);

    int update(Permission permission);

    int delete(Long id);
}
package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;

import java.util.List;

public interface PermissionMapper {

    List<Permission> findPermission(List<Role> roleList);

    List<Permission> getAllPermission();
}
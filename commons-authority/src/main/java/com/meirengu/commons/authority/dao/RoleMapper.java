package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Role;

import java.util.List;

public interface RoleMapper {

    List<Role> findRoleList(Integer accountId);

    List<Role> findRoleAll(Role role);

    Role findRoleDetail(Integer id);

    int deleteRole(Integer id);

    int insertRole(Role role);

    int updateRole(Role role);
}
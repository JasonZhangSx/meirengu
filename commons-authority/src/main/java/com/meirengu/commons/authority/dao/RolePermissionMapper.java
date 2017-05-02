package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.RolePermission;

import java.util.List;

public interface RolePermissionMapper {
    int deleteByRoleId(Long roleId);

    int insert(RolePermission record);

    int insertList(List<RolePermission> record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
}
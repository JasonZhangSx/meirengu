/**
 * @Copyright:Copyright (c) 2014
 * @Company:UQSOFT
 */
package com.meirengu.commons.authority.dao;

import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;

import java.util.List;

public interface PermissionDao extends AccessPageDao<Permission> {
    /**
     * 通过角色id获取对应的权限
     * @param roleId
     * @return
     */
    public List<Permission> getPerByRoleId(Integer roleId);

    /**
     * 通过角色id获取权限name
     * @param roleId
     * @return
     */
    public List<String> getPerStrByRoleId(Integer roleId);

    /**
     * 根据账号id删除与角色的关联数据
     * @return
     */
    public void deleteRelated(Integer accountId);

    /**
     * 添加此角色的权限关联
     * @param role
     * @return
     */
    public int saveRelated(Role role);

    /**
     * 新增权限
     * @param permission
     * @return
     */
    public int savePermission(Permission permission);

    /**
     * 获取所有的权限
     * @return
     */
    public List<Permission> getAllPermission();

    /**
     * 删除权限
     * @param idList
     * @return
     */
    public void deletePermission(List<String> idList);

    /**
     * 通过id查到权限实体
     * @param id
     * @return
     */
    public Permission getById(Integer id);

    /**
     * 修改权限
     * @param permission
     * @return
     */
    public void updatePermission(Permission permission);

    /**
     * 删除与角色相关的关联
     * @param idList
     * @return
     */
    public void deleteRelatedR(List<String> idList);

}

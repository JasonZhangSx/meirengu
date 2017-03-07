package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Role;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/16 17:11.
 */
public interface RoleService {
    /**
     * 根据用户获得角色
     * @param accountId
     * @return
     */
    List<Role> findRole(Integer accountId);
    /**
     * 根据条件获得角色
     * @param role
     * @return
     */
    List<Role> findRoleAll(Role role);

    /**
     * 根据角色id查询角色详情
     * @param id
     * @return
     */
    Role findRoleDetail(Integer id);

    /**
     * 根据角色id删除角色记录
     * @param id
     * @return
     */
    int deleteRole(Integer id);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int insertRole(Role role);

    /**
     * 更新角色
     * @param role
     * @return
     */
    int updateRole(Role role);
}

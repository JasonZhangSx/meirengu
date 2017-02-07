package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Role;

import java.util.List;

public interface RoleService extends PageBaseService<Role>{

	/**
	 * 通过账号id获取所拥有的角色
	 * @param name
	 * @return
	 */
	public List<Role> getRolesByUsername(String name);
	/**
	 * 获取所有的角色
	 * @return
	 */
	public List<Role> getAllRole();
	/**
	 * 为账号分配角色
	 * @param roleId
	 * @param accountId
	 * @return
	 */
	public int assignRoles(Integer[] roleId, Integer accountId, String accountName);
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public boolean saveRole(Role role);
	/**
	 * 删除角色
	 * @param idList
	 * @return
	 */
	public boolean deleteRole(List<String> idList);
	/**
	 * 通过id查到角色实体
	 * @param id
	 * @return
	 */
	public Role selectRole(Integer id);
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public boolean modifyRole(Role role);
	
	
}

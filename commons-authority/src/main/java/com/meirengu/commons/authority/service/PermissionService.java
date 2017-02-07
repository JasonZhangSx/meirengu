package com.meirengu.commons.authority.service;

import com.uqsoft.common.access.entity.Permission;
import com.uqsoft.common.access.service.PageBaseService;

import java.util.List;


public interface PermissionService extends PageBaseService<Permission>{

	public List<Permission> getPerByRoleId(Integer roleId);
	
	public List<String> getPerStrByRoleId(Integer roleId);
	/**
	 * 获取所有的权限
	 * @return
	 */
	public List<Permission> getAllPermission();
	/**
	 * 分配权限
	 * @param permissionId
	 * @param roleId
	 * @return
	 */
	public int assignPermission(Integer[] permissionId, Integer roleId);
	/**
	 * 保存权限
	 * @param permission
	 * @return
	 */
	public boolean savePermission(Permission permission);
	/**
	 * 删除权限
	 * @param id
	 * @return
	 */
	public boolean deletePermission(List<String> idList);
	/**
	 * 通过id查到权限实体
	 * @param id
	 * @return
	 */
	public Permission selectPermission(Integer id);
	/**
	 * 修改权限
	 * @param permission
	 * @return
	 */
	public boolean modifyPermission(Permission permission);
}

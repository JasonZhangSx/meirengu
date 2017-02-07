/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.model.User;

import java.util.List;

/**  
 * @Title:  
 * @Description:  
 * @Author:Administrator  
 * @Since:2015-4-17  
 * @Version:1.1.0  
 */
public interface RoleDao extends AccessPageDao<Role>{
	/**
	 * 通过账号id获取多对应的权限
	 * @param name
	 * @return
	 */
	public List<Role> getRolesByUsername(String name);
	
	/**
	 * 通过RoleId获取配置该角色的所有用户
	 * @param id
	 * @return
	 */
	public List<User> getUsersByRoleId(Integer id);
	
	/**
	 * 获取所有的权限
	 * @return
	 */
	public List<Role> getAllRole();
	/**
	 * 根据账号id删除与角色的关联数据
	 * @return
	 */
	public int deleteRelated(Integer accountId);
	/**
	 * 添加此账号的角色关联
	 * @param user
	 * @return
	 */
	public int saveRelated(User user);
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public int saveRole(Role role);
	/**
	 * 删除角色
	 * @param idList
	 * @return
	 */
	public int deleteRole(List<String> idList);
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
	public int updateRole(Role role);
	/**
	 * 删除与账号对应的关联
	 * @param idList
	 * @return
	 */
	public int deleteRelatedA(List<String> idList);
	/**
	 * 删除与权限对应的关联
	 * @param idList
	 * @return
	 */
	public int deleteRelatedP(List<String> idList);
	
}

/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.service.impl;

import com.uqsoft.common.access.entity.Permission;
import com.uqsoft.common.access.entity.Role;
import com.uqsoft.common.access.entity.User;
import com.uqsoft.common.access.service.PermissionService;
import com.uqsoft.common.access.service.RoleService;
import com.uqsoft.common.access.service.ShiroDbRealmServer;
import com.uqsoft.common.access.service.UserService;
import com.uqsoft.common.memcache.MemcachedClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * @Title:  
 * @Description:  
 * @Author:duyulong@uqsoft.com
 * @Since:2015-4-17  
 * @Version:1.1.0  
 */
@Service("shiroDbRealm")
public class ShiroDbRealmServerImpl extends AuthorizingRealm implements ShiroDbRealmServer {
	
	private static Log logger = LogFactory.getLog(ShiroDbRealmServerImpl.class);
	
	@Resource
	UserService userService;
	@Resource
	RoleService roleService;
	@Resource
	PermissionService permissionService;
	@Resource
	MemcachedClient memcachedClient;
	
	/**
	 * 角色、权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String loginName = principals.toString();
		return getRoleAndPerm(loginName);
	}
	
	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		User user = userService.findByUsername(authcToken.getUsername());				
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), user.getName());
		}
		return null;
	}
	
	/**
	 * 根据登录名获取角色和权限
	 * @param user
	 * @return
	 * @Description:
	 */
	private AuthorizationInfo getRoleAndPerm(String loginName){
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Map<String,List<String>> data = getRoleAndPermData(loginName);
		logger.info("roleAndPerm data is "+data);
		if(data!=null && data.size()>0){
			info.addRoles(data.get("roles"));
			info.addStringPermissions(data.get("perms"));
		}
		return info;
	}
	
	/**
	 * 根据登录名获取角色和权限数据
	 * @param user
	 * @return
	 * @Description:
	 */
	@SuppressWarnings("unchecked")
	private Map<String,List<String>> getRoleAndPermData(String loginName){
		Map<String,List<String>> data = null;
		data  = (Map<String,List<String>>)memcachedClient.get(loginName+"_UserRoleData");
		if(data==null){
			data = new HashMap<String,List<String>>();
			List<String> rListStr = new ArrayList<String>();
			List<String> pListStr = new ArrayList<String>();
			List<Permission> plst = null;
			List<Role> rList = roleService.getRolesByUsername(loginName);
			if(rList!=null && rList.size()>0){
				for (Role role : rList) {
					plst = new ArrayList<Permission>();
					
					//admin标识为超级管理员，默认拥有所有权限
					if("admin".equalsIgnoreCase(role.getValue())){
						getData(rListStr, pListStr, plst, role);
						break;
					}
					getData(rListStr, pListStr, plst, role);
				}
				data.put("roles", rListStr);
				data.put("perms", pListStr);
				memcachedClient.set(loginName+"_UserRoleData", data, 0);
			}
		}
		return data;
	}
	
	/**
	 * 根据角色获取权限数据
	 * @param rListStr
	 * @param pListStr
	 * @param plst
	 * @param role
	 * @Description:
	 */
	private void getData(List<String> rListStr, List<String> pListStr,List<Permission> plst, Role role){
		if("admin".equalsIgnoreCase(role.getValue())){
			rListStr.clear();
			pListStr.clear();
			plst = permissionService.getAllPermission();
		}else{
			plst = permissionService.getPerByRoleId(role.getId());
		}
		if(plst!=null && plst.size()>0){
			for(Permission perm: plst){
				if(!rListStr.contains(perm.getType())){
					rListStr.add(perm.getType());
				}
				if(!pListStr.contains(perm.getValue())){
					pListStr.add(perm.getValue());
				}
			}
		}
	}
}

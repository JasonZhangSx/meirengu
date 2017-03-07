/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.RoleMapper;
import com.meirengu.commons.authority.service.ShiroDbRealmServer;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class ShiroDbRealmServerImpl  implements ShiroDbRealmServer {
	//extends AuthorizingRealm
	@Override
	public AuthorizationInfo getRoleAndPerm(String loginName) {
		return null;
	}
//
//	private static Log logger = LogFactory.getLog(ShiroDbRealmServerImpl.class);
//
//	@Autowired
//	UserMapper userMapper;
//	@Autowired
//	RoleMapper roleMapper;
//	@Autowired
//	PowerMapper powerMapper;
//	@Autowired
//	MemcachedClient memcachedClient;
//
//	/**
//	 * 角色、权限验证
//	 */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(
//			PrincipalCollection principals) {
//		String loginName = principals.toString();
//		return getRoleAndPerm(loginName);
//	}
//
//	/**
//	 * 登录验证
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(
//			AuthenticationToken token) throws AuthenticationException {
//		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
////		User user = userService.findUser(authcToken.getUsername());
//		User user = userMapper.findUser(1);
//		if (user != null) {
//			return new SimpleAuthenticationInfo(user.getNickname(), user.getPassword(), user.getRealname());
//		}
//		return null;
//	}
//
//	/**
//	 * 根据登录名获取角色和权限
//	 * @return
//	 * @Description:
//	 */
//	@Override
//	public AuthorizationInfo getRoleAndPerm(String loginName){
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		Map<String,List<String>> data = getRoleAndPermData(loginName);
//		logger.info("roleAndPerm data is "+data);
//		if(data!=null && data.size()>0){
//			info.addRoles(data.get("roles"));
//			info.addStringPermissions(data.get("perms"));
//		}
//		return info;
//	}
//
//	/**
//	 * 根据登录名获取角色和权限数据
//	 * @return
//	 * @Description:
//	 */
//	@SuppressWarnings("unchecked")
//	private Map<String,List<String>> getRoleAndPermData(String loginName){
//		Map<String,List<String>> data = null;
//		data  = (Map<String,List<String>>)memcachedClient.get(loginName+"_UserRoleData");
//		if(data==null){
//			data = new HashMap<String,List<String>>();
//			List<String> rListStr = new ArrayList<String>();
//			List<String> pListStr = new ArrayList<String>();
//			List<Power> plst = null;
//			List<Role> rList = roleMapper.findRole(1);
//			if(rList!=null && rList.size()>0){
//				for (Role role : rList) {
//					plst = new ArrayList<Power>();
//					getData(rListStr, pListStr, plst, role);
//				}
//				data.put("roles", rListStr);
//				data.put("perms", pListStr);
////				memcachedClient.set(loginName+"_UserRoleData", data, 0);
//			}
//		}
//		return data;
//	}
//
//	/**
//	 * 根据角色获取权限数据
//	 * @param rListStr
//	 * @param pListStr
//	 * @param plst
//	 * @param role
//	 * @Description:
//	 */
//	private void getData(List<String> rListStr, List<String> pListStr,List<Power> plst, Role role){
////		if("admin".equalsIgnoreCase(role.getValue())){
////			rListStr.clear();
////			pListStr.clear();
////			plst = permissionService.getAllPermission();
////		}else{
//			plst = powerMapper.findPower(Integer.valueOf(role.getrId().toString()));
////		}
//		if(plst!=null && plst.size()>0){
//			for(Power perm: plst){
//				if(!rListStr.contains(perm.getpName())){
//					rListStr.add(perm.getpName());
//				}
//				if(!pListStr.contains(perm.getpValue())){
//					pListStr.add(perm.getpValue());
//				}
//			}
//		}
//	}
}

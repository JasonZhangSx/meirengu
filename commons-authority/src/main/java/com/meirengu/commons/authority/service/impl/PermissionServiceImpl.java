package com.meirengu.commons.authority.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService{

	private static Log log = LogFactory.getLog(PermissionServiceImpl.class);
	@Resource
	PermissionDao permissionDao;
	@Resource
	RoleDao roleDao;
	@Resource
	MemcachedClient memcachedClient;
	@Resource
	PageService<Permission> pageService;
	
	
	@Override
	public List<Permission> getPerByRoleId(Integer roleId) {
		return permissionDao.getPerByRoleId(roleId);
	}

	@Override
	public Page<Permission> getPageList(Page<Permission> page, Permission entity) {
		return pageService.getListByPage(page, entity, permissionDao);
	}

	@Override
	public List<Permission> getAllPermission() {
		return permissionDao.getAllPermission();
	}

	@Override
	public int assignPermission(Integer[] permissionId, Integer roleId) {
		permissionDao.deleteRelated(roleId);
		int i = 0;
		Role role = roleDao.selectRole(roleId);
		List<String> pList = new ArrayList<String>();
		if("admin".equalsIgnoreCase(role.getValue())){
			List<Permission> plst = new ArrayList<Permission>();
			plst = permissionDao.getAllPermission();
			for(Permission perm: plst){
				pList.add(perm.getValue());
			}
			role.setPermissionList(plst);
			i = permissionDao.saveRelated(role);
		}else{
			if(permissionId != null){
				List<Permission> permissionList = new ArrayList<Permission>();
				for (Integer id : permissionId) {
					permissionList.add(new Permission(id));
				}
				role.setPermissionList(permissionList);
				role.setId(roleId);
				i = permissionDao.saveRelated(role);
			}else{
				log.info(roleId+"  doesn't has any permission...");
				i = 1;
			}
		}
		//更新缓存操作
		if(!clearRoleAndPermCache(role)){
			i=0;
		}
		return i;
	}

	@Override
	public List<String> getPerStrByRoleId(Integer roleId) {
		return permissionDao.getPerStrByRoleId(roleId);
	}

	@Override
	public boolean savePermission(Permission permission) {
		try{
			permissionDao.savePermission(permission);
			return true;
		}catch(Exception e){
			log.error("permService save Perm error", e);
		}
		return false;
	}

	@Override
	public boolean deletePermission(List<String> idList) {
		try{
			permissionDao.deletePermission(idList);
			permissionDao.deleteRelatedR(idList);
			return true;
		}catch(Exception e){
			log.error("permService delete Perm error", e);
		}
		return false;
	}

	@Override
	public Permission selectPermission(Integer id) {
		return permissionDao.getById(id);
	}

	@Override
	public boolean modifyPermission(Permission permission) {
		try{
			permissionDao.updatePermission(permission);
			return true;
		}catch(Exception e){
			log.error("permService modify Perm error", e);
		}
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean clearRoleAndPermCache(Role role){
		try{
			List<User> userList = roleDao.getUsersByRoleId(role.getId());
			if(userList!=null && userList.size()>0){
				for(User user : userList){
					Map<String,List<String>> data  = (Map<String,List<String>>)memcachedClient.get(user.getName()+"_UserRoleData");
					if(data!=null && data.size()>0){
						memcachedClient.delete(user.getName()+"_UserRoleData");
					}
				}
			}
			return true;
		}catch(Exception e){
			log.error("updateRoleAndPermCache error ",e);
		}
		
		return false;
	}
}

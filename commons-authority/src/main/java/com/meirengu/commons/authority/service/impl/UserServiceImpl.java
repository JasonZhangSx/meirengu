package com.meirengu.commons.authority.service.impl;

import com.uqsoft.common.access.dao.UserDao;
import com.uqsoft.common.access.entity.Page;
import com.uqsoft.common.access.entity.User;
import com.uqsoft.common.access.service.PageService;
import com.uqsoft.common.access.service.UserService;
import com.uqsoft.common.memcache.MemcachedClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	private static Log log = LogFactory.getLog(UserServiceImpl.class);
	
	@Resource
	UserDao userDao;
	@Resource
	PageService<User> pageService;
	
	@Resource
	MemcachedClient memcachedClient;
	
	@Override
	public User getById(Integer id) {
		return userDao.findById(id);
	}

	
	@Override
	public User findByUsername(String username) {
		
		return userDao.findByUsername(username);
	}

	@Override
	public boolean saveUser(User user) {
		try{
			userDao.saveUser(user);
			return true;
		}catch(Exception e){
			log.error("userService save User error", e);
		}
		return false;
	}
	
	
	@Override
	public boolean modifyUser(User user) {
		try{
			userDao.modifyUser(user);
			return true;
		}catch(Exception e){
			log.error("userService modify User error", e);
		}
		return false;
	}
	
	@Override
	public boolean deleteUser(List<String> idList) {
		try {
			//删除角色权限缓存
			for(String id :idList){
				User user = userDao.findById(Integer.parseInt(id));
				memcachedClient.delete(user.getName()+"_UserRoleData");
			}
			//删除账号
			userDao.deleteUser(idList);
			//删除与之相关的关联
			userDao.deleteRelated(idList);
			
			
			
			log.info("change password success");
			return true;
		} catch (Exception e) {
			log.info("change password error",e);
		}
		
		return false;
	}

	public Page<User> getPageList(Page<User> page, User user){
		return pageService.getListByPage(page, user, userDao);
	}

	@Override
	public boolean changePassword(String loginName, String oldPassword,
			String newPassword) {
		try{
			User user = userDao.findByUsername(loginName);
			if(user==null){
				log.info("the user :"+loginName+" don't exist");
				return false;
			}
			if(!user.getPassword().equals(oldPassword)){
				log.info("the old password invalidate...");
				return false;
				
			}
			user.setPassword(newPassword);
			userDao.modifyUser(user);
			log.info("change password success");
			return true;
		}catch(Exception e){
			log.error("change password error ", e);
		}
		return false;
	}
}

/**
 * @Copyright:Copyright (c) 2014  
 * @Company:UQSOFT 
 */
package com.meirengu.commons.authority.dao;

import com.meirengu.commons.authority.model.User;

import java.util.List;

/**  
 * @Title:  
 * @Description:  
 * @Author:zhangjianxin  
 * @Since:2015-4-17  
 * @Version:1.1.0  
 */
public interface UserDao extends AccessPageDao<User>{
	/**
	 * 通过账号查找账号信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	/**
	 * 新增账号
	 * @param user
	 * @return
	 */
	public void saveUser(User user);
	
	/**
	 * 修改账号信息
	 * @param user
	 * @return
	 */
	public void modifyUser(User user);
	
	/**
	 * 删除账号
	 * @param idList
	 * @return
	 */
	public void deleteUser(List<String> idList);
	/**
	 * 通过id找到账号信息
	 * @param id
	 * @return
	 */
	public User findById(Integer id);
	/**
	 * 根据id删除相关联的账号
	 * @param idList
	 * @return
	 */
	public void deleteRelated(List<String> idList);
	
}

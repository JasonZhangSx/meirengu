package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.User;

import java.util.List;

public interface UserService{
	
	/**
	 * 根据ID获取用户信息
	 * @param id
	 * @return
	 * @Description:
	 */
	public User getById(Integer id);

	/**
	 * 通过账号名查询账号信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	/**
	 * 保存账号信息
	 * @param user
	 * @return
	 */
	public boolean saveUser(User user);
	
	/**
	 * 修改账号信息
	 * @param user
	 * @return
	 */
	public boolean modifyUser(User user);
	
	/**
	 * 删除账号
	 * @param idList
	 * @return
	 */
	public boolean deleteUser(List<String> idList);
	/**
	 * 修改密码
	 * @param loginName
	 * @param oldPassword
	 * @param newPassword
	 * @return 0是修改失败，1是修改成功，-2是原密码输入错误，-3是查询失败
	 */
	public boolean changePassword(String loginName, String oldPassword, String newPassword);
	
}

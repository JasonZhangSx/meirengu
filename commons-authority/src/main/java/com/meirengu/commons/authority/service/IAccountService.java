package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Account;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/18 15:58.
 */
public interface IAccountService {
    /**
     * 查找用户
     * @param account
     * @return
     */
    Account findAccount(Account account);
    /**
     * 查找用户角色及权限
     * @param account
     * @return
     */
    Account findRolePermission(Account account);

    /**
     * 用户集合
     * @param account
     * @return
     */
    List<Account> getAllUser(Account account);

    /**
     * 更新用户
     * @param account
     */
    int updateAccount(Account account);

    /**
     * 添加用户
     * @param account
     * @return
     */
    int insertAccount(Account account);
}
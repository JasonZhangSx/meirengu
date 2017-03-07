package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Account;

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
}
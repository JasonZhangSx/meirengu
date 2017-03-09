package com.meirengu.admin.service;


import com.meirengu.commons.authority.model.Account;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 16:16.
 * 账户Service
 */
public interface AccountService {
    boolean login(Account account);
    Account getRolePermission(Account account);
}

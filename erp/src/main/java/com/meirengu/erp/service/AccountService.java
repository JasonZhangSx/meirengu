package com.meirengu.erp.service;


import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.model.Organization;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 16:16.
 * 账户Service
 */
public interface AccountService {
    boolean login(Account account);
    Account getRolePermission(Account account);
    List<Account> getAllUser(Account account);
    String updateAccount(Account account);
    List<Organization> getAllOrganization(Organization organization);
    String insertAccount(Account account);
}

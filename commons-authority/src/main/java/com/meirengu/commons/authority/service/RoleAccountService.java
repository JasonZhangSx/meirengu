package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.RoleAccount;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/3 18:24.
 */
public interface RoleAccountService {
    int insertList(List<RoleAccount> list);
    int deleteByAccountId(Long accountId);
}

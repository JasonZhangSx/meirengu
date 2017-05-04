package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.RoleAccountMapper;
import com.meirengu.commons.authority.model.RoleAccount;
import com.meirengu.commons.authority.service.RoleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/3 18:24.
 */
@Service
public class RoleAccountServiceImpl implements RoleAccountService {
    @Autowired
    private RoleAccountMapper roleAccountMapper;
    @Override
    public int insertList(List<RoleAccount> list) {
        return roleAccountMapper.insertList(list);
    }

    @Override
    public int deleteByAccountId(Long accountId) {
        return roleAccountMapper.deleteByAccountId(accountId);
    }
}

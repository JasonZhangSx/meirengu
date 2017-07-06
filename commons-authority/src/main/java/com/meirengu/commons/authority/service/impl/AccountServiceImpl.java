package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.AccountMapper;
import com.meirengu.commons.authority.dao.OrganizationMapper;
import com.meirengu.commons.authority.dao.PermissionMapper;
import com.meirengu.commons.authority.dao.RoleMapper;
import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/18 16:01.
 */
@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Override
    public Account findAccount(Account account) {
        return accountMapper.findUser(account);
    }
    @Override
    public List<Account> getAllUser(Account account) {
        return accountMapper.getAllUser(account);
    }

    @Override
    public int updateAccount(Account account) {
       return accountMapper.updateAccount(account);
    }

    @Override
    public int insertAccount(Account account) {
        return accountMapper.insert(account);
    }

    @Override
    public Account findRolePermission(Account account) {
        account=accountMapper.findUser(account);
        account.setRoleList(roleMapper.findRoleList(account.getId()));
        if (account.getRoleList()!=null&&account.getRoleList().size()!=0){
            account.setPermissionsList(tree(permissionMapper.findPermission(account.getRoleList()),0));
        }
        account.setOrganization(organizationMapper.getOrganization(account.getOrganizationId()));
        return account;
    }
    /**
     * 递归遍历树形
     * @param list 查询结果
     * @param icParentId 父节点
     * @return 树形结构
     */
    public List<Permission> tree(List<Permission> list, int icParentId){
        List<Permission> permissionList = new LinkedList<>();
        for (Permission vo : list){
            if (vo.getParentId()==icParentId){
                List<Permission> voList=tree(list,vo.getId().intValue());
                vo.setList(voList);
                permissionList.add(vo);
            }
        }
        return permissionList;
    }
}

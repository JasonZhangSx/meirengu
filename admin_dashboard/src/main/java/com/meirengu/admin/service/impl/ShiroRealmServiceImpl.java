package com.meirengu.admin.service.impl;

import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.service.IAccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 12:09.
 */
@Service
public class ShiroRealmServiceImpl extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(ShiroRealmServiceImpl.class);
    @Autowired
    private IAccountService iAccountService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("Request doGetAuthorizationInfo parameter:{}",principalCollection.toString());
        principalCollection.toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Account account = new Account();
        account.setUserName(token.getUsername());
//        account.setPassword(String.valueOf(token.getPassword()));
        logger.info("Request doGetAuthenticationInfo->findAccount parameter:{}",account.toString());
        try {
            account = iAccountService.findAccount(account);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (account.getPassword().equals(String.valueOf(token.getPassword()))){
            return new SimpleAuthenticationInfo(account.getUserName(),account.getPassword(),getName());
        }
        return null;
    }
}

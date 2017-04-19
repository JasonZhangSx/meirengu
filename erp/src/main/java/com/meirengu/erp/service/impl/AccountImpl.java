package com.meirengu.erp.service.impl;

import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.service.IAccountService;
import com.meirengu.erp.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 16:18.
 */
@Service
public class AccountImpl implements AccountService {
    private final static Logger logger = LoggerFactory.getLogger(AccountImpl.class);
    @Autowired
    private IAccountService iAccountService;

    /**
     * 登录
     * @param account
     * @return
     *  TODO　暂不加密
     *  EncryptUtils.encryptMD5(user.getPassword())
     */
    @Override
    public boolean login(Account account) {
        logger.info("Request login parameter:{}", account.toString());
        Account acc = new Account();
        acc.setUserName(account.getUserName());
        acc = iAccountService.findAccount(account);
        if (acc==null){
            return false;
        }
        if (account.getPassword().equals(acc.getPassword())){
            return true;
        }
        return false;
//        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(
//                account.getUserName(),account.getPassword());
//        token.setRememberMe(true);
//        try {
//            currentUser.login(token);
//        } catch (AuthenticationException e) {
//            logger.error("login ErrorMsg:{}",e.getMessage());
//            return false;
//        }
//        if (currentUser.isAuthenticated()){
//            return true;
//        }else{
//            logger.error("login ErrorMsg:{}","账号密码错误");
//            return false;
//        }
    }

    @Override
    public Account getRolePermission(Account account) {
        logger.info("Request getRolePermission parameter:{}", account.toString());
        return iAccountService.findRolePermission(account);
    }
}

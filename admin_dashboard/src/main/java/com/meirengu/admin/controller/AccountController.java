package com.meirengu.admin.controller;

import com.meirengu.admin.service.AccountService;
import com.meirengu.admin.service.impl.DataSourceContextHolder;
import com.meirengu.commons.authority.model.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 11:23.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(Account account){
        ModelAndView modelAndView = new ModelAndView("/index");
        DataSourceContextHolder.setDbType("admin");
        if (accountService.login(account)){
            account = accountService.getRolePermission(account);
            DataSourceContextHolder.clearDbType();
            modelAndView.addObject("accounts",account);
        }
        return modelAndView;
    }
}

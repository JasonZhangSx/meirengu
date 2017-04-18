package com.meirengu.erp.controller.system;

import com.meirengu.commons.authority.model.Account;
import com.meirengu.erp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 11:23.
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(Account account){
        ModelAndView modelAndView = new ModelAndView("/index");
        if (accountService.login(account)){
            account = accountService.getRolePermission(account);
            modelAndView.addObject("accounts",account);
        }else {
            modelAndView = new ModelAndView("/system/login");
            modelAndView.addObject("msg","Account or password error!");
        }
        return modelAndView;
    }
}

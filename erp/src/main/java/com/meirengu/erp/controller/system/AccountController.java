package com.meirengu.erp.controller.system;

import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.model.Organization;
import com.meirengu.erp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 11:23.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView accountList(String realName,String userName) throws UnsupportedEncodingException {
        Account account = new Account();
        if (realName!=null&&!realName.isEmpty()){
            account.setRealName(new String((realName).getBytes("ISO-8859-1"),"utf8"));
        }
        if (userName!=null&&!userName.isEmpty()){
            account.setUserName(new String((userName).getBytes("ISO-8859-1"),"utf8"));
        }
        return new ModelAndView("/system/accountList").addObject("accountList",accountService.getAllUser(account));
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String updateAccount(Account account){
        return accountService.updateAccount(account);
    }
    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView updateOrAdd(Account account){
        ModelAndView modelAndView = new ModelAndView("/system/editAccount");
        if (account.getId()!=null){
            modelAndView.addObject("account",accountService.getRolePermission(account));
        }
        return modelAndView.addObject("organizationList",accountService.getAllOrganization(new Organization()));
    }
    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.POST)
    @ResponseBody
    public String updateAdd(Account account,String expireDate) throws ParseException {
        if (expireDate!=null&&!expireDate.isEmpty()){
            expireDate=expireDate+" 00:00:00";
            account.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireDate));
        }
        if (account.getId()!=null){
            return accountService.updateAccount(account);
        }else {
            account.setCreateTime(new Date());
            return accountService.insertAccount(account);
        }
    }
}

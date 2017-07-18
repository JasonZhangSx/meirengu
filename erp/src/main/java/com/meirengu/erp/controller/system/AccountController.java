package com.meirengu.erp.controller.system;

import com.meirengu.common.PasswordEncryption;
import com.meirengu.commons.authority.model.*;
import com.meirengu.commons.authority.service.RoleAccountService;
import com.meirengu.commons.authority.service.RoleService;
import com.meirengu.erp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/22 11:23.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAccountService roleAccountService;

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
        Account a = new Account();
        Map<Long,Long> map = new HashMap<>();
        if (account.getId()!=null){
            a = accountService.getRolePermission(account);
            modelAndView.addObject("account",a);
            for (Role role : a.getRoleList()){
                map.put(role.getId(),role.getId());
            }
            modelAndView.addObject("roleAccount",map);
        }
        modelAndView.addObject("organizationList",accountService.getAllOrganization(new Organization()));
        modelAndView.addObject("roleList",roleService.findRoleAll(null));
        return modelAndView;
    }
    @RequestMapping(value = "/updateOrAdd",method = RequestMethod.POST)
    @ResponseBody
    public String updateAdd(Account account,String expireDate,HttpServletRequest request) throws ParseException, InvalidKeySpecException, NoSuchAlgorithmException {
        String[] permission = request.getParameterValues("roleId");
        String msg;
        if (expireDate!=null&&!expireDate.isEmpty()){
            expireDate=expireDate+" 00:00:00";
            account.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireDate));
        }
        account.setPassword(PasswordEncryption.createHash(account.getPassword()));
        if (account.getId()!=null){
            msg = accountService.updateAccount(account);
        }else {
            account.setCreateTime(new Date());
            msg = accountService.insertAccount(account);
        }
        List<RoleAccount> list = new ArrayList<>();
        for (String s : permission){
            RoleAccount roleAccount = new RoleAccount();
            roleAccount.setAccountId(Long.valueOf(account.getId()));
            roleAccount.setRoleId(Long.valueOf(s));
            list.add(roleAccount);
        }
        roleAccountService.deleteByAccountId(Long.valueOf(account.getId()));
        roleAccountService.insertList(list);
        return msg;
    }
}

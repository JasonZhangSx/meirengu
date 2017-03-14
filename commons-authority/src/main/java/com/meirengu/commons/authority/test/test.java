package com.meirengu.commons.authority.test;

import com.meirengu.commons.authority.service.impl.AccountServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/8 14:23.
 */
public class test {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-spring.xml");//读取bean.xml中的内容
        AccountServiceImpl userService = ctx.getBean(AccountServiceImpl.class);//创建bean的引用对象
//        Account account = new Account();
//        account.setUserName("admin");
//        account.setPassword("123456");
//        account = userService.findAccount(account);
//        System.out.println(account.toString());
//        AccountServiceImpl accountService = new AccountServiceImpl();
        System.out.println(userService.findRolePermission(null));

        }
    }
    //new String["applicationContext-spring.xml","applicationContext-shiro.xml",
    //"applicationContext-mybatis.xml","applicationContext-datasource.xml"]

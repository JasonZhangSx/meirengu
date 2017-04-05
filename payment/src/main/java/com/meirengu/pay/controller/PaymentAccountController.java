package com.meirengu.pay.controller;

import com.meirengu.pay.service.PaymentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/15 20:31.
 */
@Controller
@RequestMapping("payAccount")
public class PaymentAccountController {
    @Autowired
    private PaymentAccountService paymentAccountService;

    /**
     * 新增资金账户信息
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addPayAccount(@RequestParam(value = "content", required = true) String content){
        return paymentAccountService.createAccount(content);
    }
    /**
     * 根据用户id修改资金账户信息
     * @param content
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public String updatePayAccount(@RequestParam(value = "content", required = true) String content){
        return paymentAccountService.updateAccount(content);
    }
    /**
     * 根据用户id查询资金账户信息
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPayAccount(@RequestParam(value = "content", required = true) String content){
        return paymentAccountService.getAccountByUserId(content);
    }

    /**
     * 实名认证
     * @param content
     * @return
     */
    @RequestMapping(value = "auth",method = RequestMethod.POST)
    @ResponseBody
    public String auth(@RequestParam(value = "content", required = true) String content){
        return paymentAccountService.auth(content);
    }
    /**
     * 校验支付密码是否正确
     * @param userId
     * @return
     */
    @RequestMapping(value = "checkPwd",method = RequestMethod.POST)
    @ResponseBody
    public String checkPwd(@RequestParam(value = "userId", required = true) Integer userId,@RequestParam(value = "pwd", required = true) String pwd){
        return paymentAccountService.checkPayPwd(userId,pwd);
    }

}

package com.meirengu.pay.controller;

import com.meirengu.pay.service.CapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/7 12:19.
 */
@Controller
@RequestMapping("capital")
public class CapitalController {
    @Autowired
    private CapitalService capitalService;

    @RequestMapping(value = "refund",method = RequestMethod.POST)
    @ResponseBody
    public String refund(@RequestParam(value = "content", required = true) String content){
        return capitalService.confirmRefund(content);
    }

    @RequestMapping(value = "withdrawals",method = RequestMethod.POST)
    @ResponseBody
    public String withdrawals(@RequestParam(value = "content", required = true) String content){
        return capitalService.confirmWithdrawals(content);
    }
}

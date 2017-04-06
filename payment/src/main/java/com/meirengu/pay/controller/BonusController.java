package com.meirengu.pay.controller;

import com.meirengu.pay.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 众筹奖励
 * Author: haoyang.Yu
 * Create Date: 2017/4/5 11:19.
 */
@Controller
@RequestMapping("bonus")
public class BonusController {
    @Autowired
    private BonusService bonusService;
    @RequestMapping(value = "invite",method = RequestMethod.POST)
    @ResponseBody
    public String invite(){
        return bonusService.invite();
    }
}

package com.meirengu.pay.controller;

import com.meirengu.pay.service.ChannelBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/3/27 16:29.
 */
@Controller
@RequestMapping("channelBank")
public class ChannelBankController {
    @Autowired
    private ChannelBankService channelBankService;

    /**
     * 新增渠道银行
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addPayAccount(@RequestParam(value = "content", required = true) String content){
        return channelBankService.insert(content);
    }
    /**
     * 修改渠道银行
     * @param content
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public String updatePayAccount(@RequestParam(value = "content", required = true) String content){
        return channelBankService.update(content);
    }
    /**
     * 查询渠道银行
     * @param bankCode
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPayAccount(@RequestParam(value = "bankCode", required = false) String bankCode){
        return channelBankService.getChannelBank(bankCode);
    }

}

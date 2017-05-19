package com.meirengu.pay.controller;

import com.meirengu.pay.service.PaymentCommitBonusService;
import com.meirengu.pay.vo.PaymentCommitBonusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/16 20:46.
 */
@Controller
@RequestMapping("paymentCommitBonus")
public class PaymentCommitBonusController {
    @Autowired
    private PaymentCommitBonusService paymentCommitBonusService;
    /**
     * 新增项目分红列表
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addPaymentCommitBonus(@RequestParam(value = "content", required = true) String content){
        return paymentCommitBonusService.bonus(content);
    }
    /**
     * 查询项目分红列表
     * @param paymentCommitBonusVo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPaymentCommitBonus(PaymentCommitBonusVo paymentCommitBonusVo){
        return paymentCommitBonusService.query(paymentCommitBonusVo);
    }
    /**
     * 开始派息
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "start")
    @ResponseBody
    public void startDistribute(){
        paymentCommitBonusService.startDistribute();
    }
}

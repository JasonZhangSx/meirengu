package com.meirengu.pay.controller;

import com.meirengu.pay.service.PaymentCommitService;
import com.meirengu.pay.vo.PaymentCommitListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/5 12:10.
 */
@Controller
@RequestMapping("paymentCommit")
public class PaymentCommitController {
    @Autowired
    private PaymentCommitService paymentCommitService;

    /**
     * 新增待打款记录
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addPaymentCommit(@RequestParam(value = "content", required = true) String content){
        return paymentCommitService.insert(content);
    }
    /**
     * 查询待打款记录
     * @param paymentCommitListVo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPaymentCommit(PaymentCommitListVo paymentCommitListVo){
        return paymentCommitService.select(paymentCommitListVo);
    }
    /**
     * 打款
     * @param paymentCommitListVo
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    @ResponseBody
    public String getPaymentCommitDetail(PaymentCommitListVo paymentCommitListVo){
        return paymentCommitService.detail(paymentCommitListVo);
    }
}

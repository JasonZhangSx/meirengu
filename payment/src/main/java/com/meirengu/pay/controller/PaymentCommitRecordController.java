package com.meirengu.pay.controller;

import com.meirengu.pay.model.PaymentCommitRecord;
import com.meirengu.pay.service.PaymentCommitRecordService;
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
@RequestMapping("paymentCommitRecord")
public class PaymentCommitRecordController {
    @Autowired
    private PaymentCommitRecordService paymentCommitRecordService;

    /**
     * 新增打款记录
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addPaymentCommitRecord(@RequestParam(value = "content", required = true) String content){
        return paymentCommitRecordService.insert(content);
    }
    /**
     * 查询打款记录
     * @param paymentCommitRecord
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPaymentCommitRecord(PaymentCommitRecord paymentCommitRecord){
        return paymentCommitRecordService.select(paymentCommitRecord);
    }
}

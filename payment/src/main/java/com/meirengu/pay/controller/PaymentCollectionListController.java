package com.meirengu.pay.controller;

import com.meirengu.pay.model.PaymentCollectionList;
import com.meirengu.pay.model.PaymentCollectionRecord;
import com.meirengu.pay.service.PaymentCollectionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/23 17:09.
 */
@Controller
@RequestMapping("paymentCollectionList")
public class PaymentCollectionListController {
    @Autowired
    private PaymentCollectionListService paymentCollectionListService;

    /**
     * 新增待收款记录
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addPaymentCommit(@RequestParam(value = "content", required = true) String content){
        return paymentCollectionListService.insert(content);
    }

    /**
     * 查询待收款
     * @param paymentCollectionList
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getPaymentCommit(PaymentCollectionList paymentCollectionList){
        return paymentCollectionListService.select(paymentCollectionList);
    }
    /**
     * 查询收款记录
     * @param paymentCollectionRecord
     * @return
     */
    @RequestMapping(value = "record",method = RequestMethod.GET)
    @ResponseBody
    public String getPaymentCommitRecord(PaymentCollectionRecord paymentCollectionRecord){
        return paymentCollectionListService.selectRecord(paymentCollectionRecord);
    }
    /**
     * 新增收款记录
     * @param content
     * @return
     */
    @RequestMapping(value = "addRecord",method = RequestMethod.POST)
    @ResponseBody
    public String addPaymentCommitRecord(@RequestParam(value = "content", required = true) String content){
        return paymentCollectionListService.addRecord(content);
    }
}

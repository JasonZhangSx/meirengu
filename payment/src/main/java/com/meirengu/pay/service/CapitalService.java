package com.meirengu.pay.service;

/**
 * 资金处理Service
 * Author: haoyang.Yu
 * Create Date: 2017/3/21 11:16.
 */
public interface CapitalService {
    /**
     * 退款确认
     * @param content 内容
     * @return 退款结果
     */
    String confirmRefund(String content);
    /**
     * 提现确认
     * @param content 内容
     * @return 退款结果
     */
    String confirmWithdrawals(String content);
}

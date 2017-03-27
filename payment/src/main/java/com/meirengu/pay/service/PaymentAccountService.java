package com.meirengu.pay.service;

/**
 * 众筹支付账户Service
 * Author: haoyang.Yu
 * Create Date: 2017/3/13 16:53.
 */
public interface PaymentAccountService {
    /**
     * 查询支付账户
     * @param content 账户信息内容
     * @return 查询结果
     */
    String getAccountByUserId(String content);
    /**
     * 创建支付账户
     * @param content 账户信息内容
     * @return 创建结果
     */
    String createAccount(String content);
    /**
     * 修改支付账户
     * @param content 账户信息内容
     * @return 修改结果
     */
    String updateAccount(String content);

    /**
     * 实名+银行卡认证
     * @param content
     * @return
     */
    String auth(String content);

}

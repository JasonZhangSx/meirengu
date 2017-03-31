package com.meirengu.pay.service;

import com.meirengu.pay.model.Payment;
import com.meirengu.pay.vo.WxNotifyData;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付相关
 * @author 建新
 * @create 2017-02-23 14:52
 */
public interface PaymentService {

    int insert(Payment payment);

    int update(Payment payment);

    boolean paySuccess(WxNotifyData notifyData, HttpServletRequest request, String returnMsg);

    boolean payFail(WxNotifyData notifyData, HttpServletRequest request, String returnMsg);

    Payment detail(Payment payment);

    /**
     * 退款申请-yhy
     * @param content
     * @return
     */
    String refund(String content);

    /**
     * 提现申请-yhy
     * @param content
     * @return
     */
    String withdrawals(String content);

    /**
     * 充值申请-yhy
     * @param content
     * @return
     */
    String recharge(String content);

    /**
     * 支付申请-yhy
     * @param content
     * @return
     */
    String payment(String content);

    /**
     * 宝付回调-yhy
     * @param content
     * @return
     */
    String baofuCallback(String content);

    /**
     * 根据用户id查询交易记录
     * @param userId
     * @return
     */
    String getPaymentRecord(String userId);
    /**
     * 根据用户id查询提现金额
     * @param userId
     * @return
     */
    String getWithdrawalsAmount(String userId);
}

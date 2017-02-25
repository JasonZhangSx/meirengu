package com.meirengu.pay.service;

import com.meirengu.pay.model.Payment;
import com.meirengu.pay.vo.WxNotifyData;

/**
 * 支付相关
 * @author 建新
 * @create 2017-02-23 14:52
 */
public interface PaymentService {

    int insert(Payment payment);

    int update(Payment payment);

    boolean paySuccess(WxNotifyData notifyData);

    boolean payFail();
}

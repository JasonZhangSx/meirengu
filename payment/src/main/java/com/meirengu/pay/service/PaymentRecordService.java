
package com.meirengu.pay.service;

import com.meirengu.pay.model.PaymentRecord;
import com.meirengu.pay.utils.PaymentException;

/**
 * 资金流水Service
 * Author: haoyang.Yu
 * Create Date: 2017/3/14 11:33.
 */
public interface PaymentRecordService {
    /**
     * 记录支付流水
     * @param paymentRecord
     * @return
     */
    String insertPaymentRecord(PaymentRecord paymentRecord);
}

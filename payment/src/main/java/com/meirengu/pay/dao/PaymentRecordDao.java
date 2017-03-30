package com.meirengu.pay.dao;


import com.meirengu.pay.vo.PaymentRecordVo;

import java.util.List;

public interface PaymentRecordDao {
    int insertPaymentRecord(PaymentRecordVo record);

    PaymentRecordVo selectPaymentRecord(PaymentRecordVo record);

    Integer selectPaymentRecordCount(PaymentRecordVo record);

    int updatePaymentRecord(PaymentRecordVo record);

    List<PaymentRecordVo> getPaymentRecord(PaymentRecordVo paymentRecordVo);
}
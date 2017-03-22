package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentRecord;

public interface PaymentRecordDao {
    int insertPaymentRecord(PaymentRecord record);

    PaymentRecord selectPaymentRecord(PaymentRecord record);

    int updatePaymentRecord(PaymentRecord record);
}
package com.meirengu.pay.vo;

import com.meirengu.pay.model.PaymentCommitList;
import com.meirengu.pay.model.PaymentCommitRecord;
import com.meirengu.pay.utils.check.ValidateNotNull;
import com.meirengu.pay.utils.check.ValidatePattern;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/4 19:56.
 */
public class PaymentCommitListVo extends PaymentCommitList {
    @ValidateNotNull(attributeValue = "项目完成时间")
    @ValidatePattern(attributeValue = "项目完成时间",pattern="\\d{4}-\\d{2}-\\d{2}")
    private String endDate;

    private List<PaymentCommitRecord> list;
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<PaymentCommitRecord> getList() {
        return list;
    }

    public void setList(List<PaymentCommitRecord> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PaymentCommitListVo{" +
                "endDate='" + endDate + '\'' +
                ", list=" + list +
                "} " + super.toString();
    }
}

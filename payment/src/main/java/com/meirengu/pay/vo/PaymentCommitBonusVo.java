package com.meirengu.pay.vo;

import com.meirengu.pay.model.PaymentCommitBonus;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/17 17:23.
 */
public class PaymentCommitBonusVo extends PaymentCommitBonus {
    private String startDate;

    private String endDate;

    private String shouldTimeStart;

    private String shouldTimeEnd;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShouldTimeStart() {
        return shouldTimeStart;
    }

    public void setShouldTimeStart(String shouldTimeStart) {
        this.shouldTimeStart = shouldTimeStart;
    }

    public String getShouldTimeEnd() {
        return shouldTimeEnd;
    }

    public void setShouldTimeEnd(String shouldTimeEnd) {
        this.shouldTimeEnd = shouldTimeEnd;
    }

    @Override
    public String toString() {
        return "PaymentCommitBonusVo{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", shouldTimeStart='" + shouldTimeStart + '\'' +
                ", shouldTimeEnd='" + shouldTimeEnd + '\'' +
                "} " + super.toString();
    }
}

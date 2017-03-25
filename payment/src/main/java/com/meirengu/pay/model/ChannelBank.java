package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.AnnotationValidable;

import java.util.Date;

public class ChannelBank implements AnnotationValidable {
    private Integer bankId;

    private Integer channelId;

    private String bankCode;

    private String bankName;

    private Short singleLimitAmout;

    private Short dayLimitAmount;

    private Date createTime;

    private Date updateTime;

    private String operateAccount;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Short getSingleLimitAmout() {
        return singleLimitAmout;
    }

    public void setSingleLimitAmout(Short singleLimitAmout) {
        this.singleLimitAmout = singleLimitAmout;
    }

    public Short getDayLimitAmount() {
        return dayLimitAmount;
    }

    public void setDayLimitAmount(Short dayLimitAmount) {
        this.dayLimitAmount = dayLimitAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount == null ? null : operateAccount.trim();
    }

    @Override
    public String toString() {
        return "ChannelBank{" +
                "bankId=" + bankId +
                ", channelId=" + channelId +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", singleLimitAmout=" + singleLimitAmout +
                ", dayLimitAmount=" + dayLimitAmount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operateAccount='" + operateAccount + '\'' +
                '}';
    }
}
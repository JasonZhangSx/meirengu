package com.meirengu.pay.model;

import java.util.Date;

public class PaymentChannel {
    private Integer channelId;

    private String channelName;

    private String bankName;

    private Short singleLimitAmout;

    private Short dayLimitAmount;

    private Date createTime;

    private Date updateTime;

    private String operateAccount;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
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
}
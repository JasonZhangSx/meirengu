package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentAccount implements  AnnotationValidable {
    private Integer accountId;

    private String accountName;

    private Integer accountType;

    private String currencyType;
    @ValidateNotNull(attributeValue = "用户Id")
    @ValidateDigit
    @ValidateSize(attributeValue = "用户Id",minSize = "8",maxSize = "9")
    private Integer userId;

    private String password;
    @ValidateNotNull(attributeValue = "手机号")
    @ValidatePattern(attributeValue = "手机号",pattern="^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\\\d{8}$")
    private String mobile;
    @ValidatePattern(attributeValue = "邮箱",pattern="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    private String email;

    private Byte status;

    private BigDecimal accountBalance;

    private Date createTime;

    private Date updateTime;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType == null ? null : currencyType.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
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

    @Override
    public String toString() {
        return "PaymentAccount{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", accountType=" + accountType +
                ", currencyType='" + currencyType + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", accountBalance=" + accountBalance +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
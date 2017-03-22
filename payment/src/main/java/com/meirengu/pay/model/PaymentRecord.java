package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.*;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentRecord implements AnnotationValidable {
    private Integer paymentId;
    @ValidateNotNull(attributeValue = "用户id")
    @ValidateDigit()
    @ValidateSize(attributeValue = "用户Id",minSize = "8",maxSize = "9")
    private Integer userId;

    private String userName;

    private String userPhone;

    private Integer accountId;

    private Integer partnerId;
    @ValidateNotNull(attributeValue = "支付方式")
    @ValidateInt(attributeValue = "支付方式",min = 0,max = 1)
    private Integer paymentMethod;

    private Integer paymentChannel;

    @ValidateSize(attributeValue = "银行代码",minSize = "3",maxSize = "4")
    private String paymentBankType;
    @ValidateNotNull(attributeValue = "支付类型")
    @ValidateInt(attributeValue = "支付类型",min = 1,max = 4)
    private Integer paymentType;

    private Date channelRequestTime;

    private Date channelResponseTime;

    private String orderSn;

    private String transactionSn;

    private Integer itemId;

    private String itemName;

    private Integer itemLevelId;

    private String itemLevelName;

    @ValidateNotNull(attributeValue = "订单总金额")
    private BigDecimal orderAmount;
    @ValidateNotNull(attributeValue = "支付总金额")
    private BigDecimal paymentAmount;

    private BigDecimal rebateAmount;

    private BigDecimal paymentFrontBalance;

    private BigDecimal paymentBackBalance;

    private Integer status;

    private String deviceInfo;

    private Date createTime;

    private Date updateTime;

    private String returnMsg;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentBankType() {
        return paymentBankType;
    }

    public void setPaymentBankType(String paymentBankType) {
        this.paymentBankType = paymentBankType == null ? null : paymentBankType.trim();
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Date getChannelRequestTime() {
        return channelRequestTime;
    }

    public void setChannelRequestTime(Date channelRequestTime) {
        this.channelRequestTime = channelRequestTime;
    }

    public Date getChannelResponseTime() {
        return channelResponseTime;
    }

    public void setChannelResponseTime(Date channelResponseTime) {
        this.channelResponseTime = channelResponseTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn == null ? null : transactionSn.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Integer getItemLevelId() {
        return itemLevelId;
    }

    public void setItemLevelId(Integer itemLevelId) {
        this.itemLevelId = itemLevelId;
    }

    public String getItemLevelName() {
        return itemLevelName;
    }

    public void setItemLevelName(String itemLevelName) {
        this.itemLevelName = itemLevelName == null ? null : itemLevelName.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public BigDecimal getPaymentFrontBalance() {
        return paymentFrontBalance;
    }

    public void setPaymentFrontBalance(BigDecimal paymentFrontBalance) {
        this.paymentFrontBalance = paymentFrontBalance;
    }

    public BigDecimal getPaymentBackBalance() {
        return paymentBackBalance;
    }

    public void setPaymentBackBalance(BigDecimal paymentBackBalance) {
        this.paymentBackBalance = paymentBackBalance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
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

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg == null ? null : returnMsg.trim();
    }

    @Override
    public String toString() {
        return "PaymentRecord{" +
                "paymentId=" + paymentId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", accountId=" + accountId +
                ", partnerId=" + partnerId +
                ", paymentMethod=" + paymentMethod +
                ", paymentChannel=" + paymentChannel +
                ", paymentBankType='" + paymentBankType + '\'' +
                ", paymentType=" + paymentType +
                ", channelRequestTime=" + channelRequestTime +
                ", channelResponseTime=" + channelResponseTime +
                ", orderSn='" + orderSn + '\'' +
                ", transactionSn='" + transactionSn + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemLevelId=" + itemLevelId +
                ", itemLevelName='" + itemLevelName + '\'' +
                ", orderAmount=" + orderAmount +
                ", paymentAmount=" + paymentAmount +
                ", rebateAmount=" + rebateAmount +
                ", paymentFrontBalance=" + paymentFrontBalance +
                ", paymentBackBalance=" + paymentBackBalance +
                ", status=" + status +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}
package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateDigit;
import com.meirengu.pay.utils.check.ValidateNotNull;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCollectionList implements AnnotationValidable {
    private Integer id;
    @ValidateNotNull(attributeValue = "合作方id")
    @ValidateDigit
    private Integer partnerId;
    @ValidateNotNull(attributeValue = "合作方名称")
    private String partnerName;
    @ValidateNotNull(attributeValue = "众筹项目id")
    @ValidateDigit
    private Integer itemId;
    @ValidateNotNull(attributeValue = "众筹项目名称")
    private String itemName;
    @ValidateNotNull(attributeValue = "目标金额")
    private BigDecimal targetAmount;
//    @ValidateNotNull(attributeValue = "完成金额")
    private BigDecimal completedAmount;
    //本金
    private BigDecimal principal;
    //利息
    private BigDecimal interest;

//    @ValidateNotNull(attributeValue = "收款期数")
    private Integer collectionPeriod;

    private Integer receivedPeriod;

    private Integer surplusPeriod;

    private Date surplusTime;

    private Integer countdown;

    private Byte status;

    private Date collectionTime;

    private String collectionaccount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName == null ? null : partnerName.trim();
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

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(BigDecimal completedAmount) {
        this.completedAmount = completedAmount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getCollectionPeriod() {
        return collectionPeriod;
    }

    public void setCollectionPeriod(Integer collectionPeriod) {
        this.collectionPeriod = collectionPeriod;
    }

    public Integer getReceivedPeriod() {
        return receivedPeriod;
    }

    public void setReceivedPeriod(Integer receivedPeriod) {
        this.receivedPeriod = receivedPeriod;
    }

    public Integer getSurplusPeriod() {
        return surplusPeriod;
    }

    public void setSurplusPeriod(Integer surplusPeriod) {
        this.surplusPeriod = surplusPeriod;
    }

    public Date getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(Date surplusTime) {
        this.surplusTime = surplusTime;
    }

    public Integer getCountdown() {
        return countdown;
    }

    public void setCountdown(Integer countdown) {
        this.countdown = countdown;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionaccount() {
        return collectionaccount;
    }

    public void setCollectionaccount(String collectionaccount) {
        this.collectionaccount = collectionaccount == null ? null : collectionaccount.trim();
    }
}
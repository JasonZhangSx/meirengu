package com.meirengu.pay.model;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCollectionList {
    private Integer id;

    private Integer partnerId;

    private String partnerName;

    private Integer itemId;

    private String itemName;

    private BigDecimal targetAmount;

    private BigDecimal completedAmount;

    private BigDecimal principal;

    private BigDecimal interest;

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
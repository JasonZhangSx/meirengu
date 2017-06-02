package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateDigit;
import com.meirengu.pay.utils.check.ValidateNotNull;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCollectionRecord implements AnnotationValidable {
    private Integer id;
    @ValidateNotNull(attributeValue = "待收款ID")
    @ValidateDigit
    private Integer paymentCollectionId;

    private Integer partnerId;

    private String partnerName;

    private Integer itemId;

    private String itemName;
    @ValidateNotNull(attributeValue = "收款期数")
    @ValidateDigit
    private Integer collectionPeriod;

    private Integer collectionType;

    private BigDecimal shouldAmount;
    @ValidateNotNull(attributeValue = "实收款金额")
    private BigDecimal actualAmount;

    private Integer status;

    private String imageCredential;

    private Date collectionTime;

    private String collectionAccount;

    private String interestRate;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentCollectionId() {
        return paymentCollectionId;
    }

    public void setPaymentCollectionId(Integer paymentCollectionId) {
        this.paymentCollectionId = paymentCollectionId;
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

    public Integer getCollectionPeriod() {
        return collectionPeriod;
    }

    public void setCollectionPeriod(Integer collectionPeriod) {
        this.collectionPeriod = collectionPeriod;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public BigDecimal getShouldAmount() {
        return shouldAmount;
    }

    public void setShouldAmount(BigDecimal shouldAmount) {
        this.shouldAmount = shouldAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getImageCredential() {
        return imageCredential;
    }

    public void setImageCredential(String imageCredential) {
        this.imageCredential = imageCredential == null ? null : imageCredential.trim();
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionAccount() {
        return collectionAccount;
    }

    public void setCollectionAccount(String collectionAccount) {
        this.collectionAccount = collectionAccount == null ? null : collectionAccount.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "PaymentCollectionRecord{" +
                "id=" + id +
                ", paymentCollectionId=" + paymentCollectionId +
                ", partnerId=" + partnerId +
                ", partnerName='" + partnerName + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", collectionPeriod=" + collectionPeriod +
                ", collectionType=" + collectionType +
                ", shouldAmount=" + shouldAmount +
                ", actualAmount=" + actualAmount +
                ", status=" + status +
                ", imageCredential='" + imageCredential + '\'' +
                ", collectionTime=" + collectionTime +
                ", collectionAccount='" + collectionAccount + '\'' +
                ", interestRate='" + interestRate + '\'' +
                '}';
    }
}
package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateDigit;
import com.meirengu.pay.utils.check.ValidateNotNull;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCommitRecord implements AnnotationValidable {
    private Integer id;
    @ValidateNotNull(attributeValue = "待打款ID")
    @ValidateDigit
    private Integer paymentCommitId;

    private Integer partnerId;

    private String partnerName;

    private Integer itemId;

    private String itemName;
    @ValidateNotNull(attributeValue = "打款类型；1首款；2尾款")
    @ValidateDigit
    private Integer commitType;
    @ValidateNotNull(attributeValue = "应付款")
    private BigDecimal shouldAmount;
    @ValidateNotNull(attributeValue = "实付款")
    private BigDecimal actualAmount;
    @ValidateNotNull(attributeValue = "凭据")
    private String imageCredential;

    private Date commitTime;

    private String commitAccount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentCommitId() {
        return paymentCommitId;
    }

    public void setPaymentCommitId(Integer paymentCommitId) {
        this.paymentCommitId = paymentCommitId;
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

    public Integer getCommitType() {
        return commitType;
    }

    public void setCommitType(Integer commitType) {
        this.commitType = commitType;
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

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitAccount() {
        return commitAccount;
    }

    public void setCommitAccount(String commitAccount) {
        this.commitAccount = commitAccount == null ? null : commitAccount.trim();
    }

    @Override
    public String toString() {
        return "PaymentCommitRecord{" +
                "id=" + id +
                ", paymentCommitId=" + paymentCommitId +
                ", partnerId=" + partnerId +
                ", partnerName='" + partnerName + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", commitType=" + commitType +
                ", shouldAmount=" + shouldAmount +
                ", actualAmount=" + actualAmount +
                ", imageCredential='" + imageCredential + '\'' +
                ", commitTime=" + commitTime +
                ", commitAccount='" + commitAccount + '\'' +
                '}';
    }
}
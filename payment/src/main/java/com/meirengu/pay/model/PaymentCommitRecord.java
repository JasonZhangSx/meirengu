package com.meirengu.pay.model;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCommitRecord {
    private Integer id;

    private Integer partnerId;

    private String partnerName;

    private Integer itemId;

    private String itemName;

    private Integer commitType;

    private BigDecimal shouldAmount;

    private BigDecimal actualAmount;

    private String imageCredential;

    private Date commitTime;

    private String commitAccount;

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
}
package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateDigit;
import com.meirengu.pay.utils.check.ValidateNotNull;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCommitList implements AnnotationValidable {
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
    @ValidateNotNull(attributeValue = "完成金额")
    private BigDecimal completedAmount;

    private Date completedTime;
    @ValidateNotNull(attributeValue = "放款方式")
    @ValidateDigit
    private Integer loanMode;
    @ValidateNotNull(attributeValue = "首款比例")
    private Short firstRatio;
    @ValidateNotNull(attributeValue = "尾款比例")
    private Short endRatio;

    private Byte status;

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

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public Integer getLoanMode() {
        return loanMode;
    }

    public void setLoanMode(Integer loanMode) {
        this.loanMode = loanMode;
    }

    public Short getFirstRatio() {
        return firstRatio;
    }

    public void setFirstRatio(Short firstRatio) {
        this.firstRatio = firstRatio;
    }

    public Short getEndRatio() {
        return endRatio;
    }

    public void setEndRatio(Short endRatio) {
        this.endRatio = endRatio;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        return "PaymentCommitList{" +
                "id=" + id +
                ", partnerId=" + partnerId +
                ", partnerName='" + partnerName + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", targetAmount=" + targetAmount +
                ", completedAmount=" + completedAmount +
                ", completedTime=" + completedTime +
                ", loanMode=" + loanMode +
                ", firstRatio=" + firstRatio +
                ", endRatio=" + endRatio +
                ", status=" + status +
                ", commitTime=" + commitTime +
                ", commitAccount='" + commitAccount + '\'' +
                '}';
    }
}
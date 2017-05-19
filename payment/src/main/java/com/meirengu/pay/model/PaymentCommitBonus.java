package com.meirengu.pay.model;

import com.meirengu.pay.utils.check.AnnotationValidable;
import com.meirengu.pay.utils.check.ValidateDigit;
import com.meirengu.pay.utils.check.ValidateNotNull;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentCommitBonus implements AnnotationValidable {
    private Integer id;

    private Integer userId;

    private String userName;

    private String userPhone;
    @ValidateNotNull(attributeValue = "众筹项目id")
    @ValidateDigit
    private Integer itemId;
    @ValidateNotNull(attributeValue = "众筹项目名称")
    private String itemName;

    private Integer itemLevelId;

    private String itemLevelName;

    private Integer number;

    private BigDecimal investPrincipal;

    private Integer yearRate;

    private Integer period;

    private BigDecimal principal;

    private BigDecimal income;

    private BigDecimal allowance;

    private BigDecimal totalAmount;

    private Date shouldTime;

    private Integer status;

    private Date bonusTime;

    private String bonusAccount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getInvestPrincipal() {
        return investPrincipal;
    }

    public void setInvestPrincipal(BigDecimal investPrincipal) {
        this.investPrincipal = investPrincipal;
    }

    public Integer getYearRate() {
        return yearRate;
    }

    public void setYearRate(Integer yearRate) {
        this.yearRate = yearRate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getAllowance() {
        return allowance;
    }

    public void setAllowance(BigDecimal allowance) {
        this.allowance = allowance;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBonusTime() {
        return bonusTime;
    }

    public void setBonusTime(Date bonusTime) {
        this.bonusTime = bonusTime;
    }

    public String getBonusAccount() {
        return bonusAccount;
    }

    public void setBonusAccount(String bonusAccount) {
        this.bonusAccount = bonusAccount == null ? null : bonusAccount.trim();
    }

    public Date getShouldTime() {
        return shouldTime;
    }

    public void setShouldTime(Date shouldTime) {
        this.shouldTime = shouldTime;
    }

    @Override
    public String toString() {
        return "PaymentCommitBonus{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemLevelId=" + itemLevelId +
                ", itemLevelName='" + itemLevelName + '\'' +
                ", number=" + number +
                ", investPrincipal=" + investPrincipal +
                ", yearRate=" + yearRate +
                ", period=" + period +
                ", principal=" + principal +
                ", income=" + income +
                ", allowance=" + allowance +
                ", totalAmount=" + totalAmount +
                ", shouldTime=" + shouldTime +
                ", status=" + status +
                ", bonusTime=" + bonusTime +
                ", bonusAccount='" + bonusAccount + '\'' +
                '}';
    }
}
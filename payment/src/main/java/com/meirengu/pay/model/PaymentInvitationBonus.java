package com.meirengu.pay.model;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentInvitationBonus {
    private Integer id;

    private Integer userId;

    private BigDecimal principal;

    private BigDecimal investPrincipal;

    private String investmentTime;

    private Date createTime;

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

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInvestPrincipal() {
        return investPrincipal;
    }

    public void setInvestPrincipal(BigDecimal investPrincipal) {
        this.investPrincipal = investPrincipal;
    }

    public String getInvestmentTime() {
        return investmentTime;
    }

    public void setInvestmentTime(String investmentTime) {
        this.investmentTime = investmentTime == null ? null : investmentTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PaymentInvitationBonus{" +
                "id=" + id +
                ", userId=" + userId +
                ", principal=" + principal +
                ", investPrincipal=" + investPrincipal +
                ", investmentTime='" + investmentTime + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
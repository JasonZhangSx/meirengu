package com.meirengu.uc.model;

import com.meirengu.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huoyan403 on 3/23/2017.
 */
public class Inviter extends BaseObject{

    private Integer id;
    private Integer userId;
    private Integer invitedUserId;
    private String invitedUserPhone;
    private Date registerTime;
    private Date investTime;
    private BigDecimal reward;

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

    public Integer getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(Integer invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public String getInvitedUserPhone() {
        return invitedUserPhone;
    }

    public void setInvitedUserPhone(String invitedUserPhone) {
        this.invitedUserPhone = invitedUserPhone;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }
}

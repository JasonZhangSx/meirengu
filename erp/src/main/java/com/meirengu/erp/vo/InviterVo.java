package com.meirengu.erp.vo;

import com.meirengu.erp.utils.ExcelField;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huoyan403 on 4/26/2017.
 * 邀请分导出使用bean
 */
public class InviterVo {

    private Integer sex;
    private Integer isBuy;
    private String email;
    private Integer state;
    private String phone;
    private String avatar;
    private Integer isAuth;
    private String idCard;
    private Integer areaId;
    private String loginIp;
    private String nickname;
    private String realname;
    private String bankCode;
    private Integer loginNum;
//    private Long birthday;
    private String bankPhone;
    private Long loginTime;
    private Integer loginFrom;
    private String lastLoginIp;
    private String bankIdCard;
    private Integer isAllowTalk;
    private Long registerTime;
    private Integer registerFrom;
    private Integer isAllowInform;
    private Long lastLoginTime;
    private BigDecimal invitedreward;
    private String totalInvestMoney;
    private String invitedUserPhone;
    private Long invitedInvestTime;
    private Long invitedRegisterTime;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    @ExcelField(title="邀请人",align=2, sort=100)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    @ExcelField(title="邀请人昵称",align=2, sort=120)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @ExcelField(title="邀请人实名",align=2, sort=140)
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }


    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }

    public String getLoginTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(loginTime));
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(Integer loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getBankIdCard() {
        return bankIdCard;
    }

    public void setBankIdCard(String bankIdCard) {
        this.bankIdCard = bankIdCard;
    }

    public Integer getIsAllowTalk() {
        return isAllowTalk;
    }

    public void setIsAllowTalk(Integer isAllowTalk) {
        this.isAllowTalk = isAllowTalk;
    }

    @ExcelField(title="邀请人注册时间",align=2, sort=160)
    public String getRegisterTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(registerTime));
    }
    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    @ExcelField(title="邀请人注册来源",align=2, sort=180)
    public Integer getRegisterFrom() {
        return registerFrom;
    }
    public void setRegisterFrom(Integer registerFrom) {
        this.registerFrom = registerFrom;
    }

    public Integer getIsAllowInform() {
        return isAllowInform;
    }

    public void setIsAllowInform(Integer isAllowInform) {
        this.isAllowInform = isAllowInform;
    }

    public String getLastLoginTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(lastLoginTime));
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    @ExcelField(title="邀请人邀请奖励",align=2, sort=200)
    public BigDecimal getInvitedreward() {
        return invitedreward;
    }

    public void setInvitedreward(BigDecimal invitedreward) {
        this.invitedreward = invitedreward;
    }
    @ExcelField(title="邀请人累计投资额",align=2, sort=220)
    public String getTotalInvestMoney() {
        return totalInvestMoney;
    }

    public void setTotalInvestMoney(String totalInvestMoney) {
        this.totalInvestMoney = totalInvestMoney;
    }

    @ExcelField(title="被邀请人手机号",align=2, sort=240)
    public String getInvitedUserPhone() {
        return invitedUserPhone;
    }
    public void setInvitedUserPhone(String invitedUserPhone) {
        this.invitedUserPhone = invitedUserPhone;
    }
    @ExcelField(title="被邀请人投资时间",align=2, sort=260)
    public String getInvitedInvestTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(invitedInvestTime));
    }

    public void setInvitedInvestTime(Long invitedInvestTime) {
        this.invitedInvestTime = invitedInvestTime;
    }
    @ExcelField(title="邀请人注册时间",align=2, sort=280)
    public String getInvitedRegisterTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(invitedRegisterTime));
    }

    public void setInvitedRegisterTime(Long invitedRegisterTime) {
        this.invitedRegisterTime = invitedRegisterTime;
    }
}

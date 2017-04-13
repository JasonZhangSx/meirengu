package com.meirengu.erp.model;

import com.meirengu.erp.utils.ExcelField;
import com.meirengu.model.BaseObject;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author Marvin
 * @create 2017-01-10 下午2:41
 */
public class User extends BaseObject {

    private Integer userId;
    private String nickname;
    private String realname;
    private String phone;
    private String mobileInviter;
    private String idCard;
    private String password;

    private String bankCode;
    private String bankIdCard;
    private String bankPhone;
    private String avatar;
    private Integer sex;
//    private Date birthday;
    private String email;
    private String qq;
    private String wx;
    private Integer areaId;
    private Integer registerFrom;
//    private Date registerTime;
    private Integer loginFrom;
    private String loginIp;
//    private Date loginTime;
    private String lastLoginIp;
//    private Date lastLoginTime;
    private Integer loginNum;
    private String wxOpenid;
    private String wxInfo;
    private String qqOpenid;
    private String qqInfo;
    private String sinaOpenid;
    private String sinaInfo;
    private Integer isAuth;
    private Integer isBuy;
    private Integer isAllowInform;
    private Integer isAllowTalk;
    private Integer state;

    private Integer investConditions;
    @ExcelField(title="用户投资条件",align=2, sort=100)
    public String getInvestConditions() {
        if(investConditions==1){
            return "专业投资人";
        }
        if(investConditions==2){
            return "投资30万";
        }
        if(investConditions==3){
            return "投资100万";
        }
        return "未选择";
    }

    public void setInvestConditions(Integer investConditions) {
        this.investConditions = investConditions;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @ExcelField(title="用户昵称",align=2, sort=10)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @ExcelField(title="用户真实姓名",align=2, sort=20)
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    @ExcelField(title="用户手机号",align=2, sort=30)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobileInviter() {
        return mobileInviter;
    }

    public void setMobileInviter(String mobileInviter) {
        this.mobileInviter = mobileInviter;
    }
    @ExcelField(title="用户身份证号",align=2, sort=40)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankIdCard() {
        return bankIdCard;
    }

    public void setBankIdCard(String bankIdCard) {
        this.bankIdCard = bankIdCard;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(Integer registerFrom) {
        this.registerFrom = registerFrom;
    }

    public Integer getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(Integer loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getWxInfo() {
        return wxInfo;
    }

    public void setWxInfo(String wxInfo) {
        this.wxInfo = wxInfo;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getQqInfo() {
        return qqInfo;
    }

    public void setQqInfo(String qqInfo) {
        this.qqInfo = qqInfo;
    }

    public String getSinaOpenid() {
        return sinaOpenid;
    }

    public void setSinaOpenid(String sinaOpenid) {
        this.sinaOpenid = sinaOpenid;
    }

    public String getSinaInfo() {
        return sinaInfo;
    }

    public void setSinaInfo(String sinaInfo) {
        this.sinaInfo = sinaInfo;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getIsAllowInform() {
        return isAllowInform;
    }

    public void setIsAllowInform(Integer isAllowInform) {
        this.isAllowInform = isAllowInform;
    }

    public Integer getIsAllowTalk() {
        return isAllowTalk;
    }

    public void setIsAllowTalk(Integer isAllowTalk) {
        this.isAllowTalk = isAllowTalk;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}

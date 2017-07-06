package com.meirengu.uc.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/**
 * 会员实体类
 *
 * @author Marvin
 * @create 2017-01-10 下午2:41
 */
public class User extends BaseObject {

    private static final long serialVersionUID = -1785028199316832013L;
    /**
     * 会员id
     **/
    private Integer userId;
    /**
     * 会员昵称
     **/
    private String nickname;
    /**
     * 会员真实姓名
     **/
    private String realname;
    /**
     * 会员手机号
     **/
    private String phone;
    /**
     * 邀请人手机号
     **/
    private String mobileInviter;
    /**
     * 会员身份证号
     **/
    private String idCard;
    /**
     * 会员密码
     **/
    private String password;

    private String bankCode;
    /**
     * 会员绑定银行卡号
     **/
    private String bankIdCard;
    /**
     * 会员银行预留手机号
     **/
    private String bankPhone;
    /**
     * 会员头像
     **/
    private String avatar;
    /**
     * 会员性别
     **/
    private Integer sex;
    /**
     * 会员生日
     **/
    private Date birthday;
    /**
     * 会员邮箱
     **/
    private String email;
    /**
     * 会员qq
     **/
    private String qq;
    /**
     * 会员微信
     **/
    private String wx;
    /**
     * 会员新浪昵称
     */
    private String sina;
    /**
     * 会员区域id
     **/
    private Integer areaId;
    /**
     * 会员注册来源
     **/
    private Integer registerFrom;
    /**
     * 会员注册时间
     **/
    private Date registerTime;
    /**
     * 会员登录来源
     **/
    private Integer loginFrom;
    /**
     * 会员登录ip
     **/
    private String loginIp;
    /**
     * 会员登录时间
     **/
    private Date loginTime;
    /**
     * 会员上一次登录ip
     **/
    private String lastLoginIp;
    /**
     * 会员上一次登录时间
     **/
    private Date lastLoginTime;
    /**
     * 会员登录次数
     **/
    private Integer loginNum;
    /**
     * 会员微信互联id
     **/
    private String wxOpenid;
    /**
     * 会员微信互联相关信息
     **/
    private String wxInfo;
    /**
     * 会员qq互联id
     **/
    private String qqOpenid;
    /**
     * 会员qq互联相关信息
     **/
    private String qqInfo;
    /**
     * 会员微博互联id
     **/
    private String sinaOpenid;
    /**
     * 会员微博互联账号相关信息
     **/
    private String sinaInfo;
    /**
     * 会员是否认证
     **/
    private Integer isAuth;
    /**
     * 会员是否允许购买商品
     **/
    private Integer isBuy;
    /**
     * 会员是否允许举报(1可以/0不可以)
     **/
    private Integer isAllowInform;
    /**
     * 会员是否有咨询和发送站内信的权限 1为开启 0为关闭
     **/
    private Integer isAllowTalk;
    /**
     * 会员的开启状态 1为开启 0为关闭
     **/
    private Integer state;

    private Integer investConditions;
    //等级
    private Integer level;
    //公司
    private String company;
    //职位
    private String position;
    //个人简介
    private String introduction;


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getInvestConditions() {
        return investConditions;
    }

    public void setInvestConditions(Integer investConditions) {
        this.investConditions = investConditions;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
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

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }
}

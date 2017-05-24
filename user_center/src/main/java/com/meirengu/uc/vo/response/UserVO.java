package com.meirengu.uc.vo.response;

/**
 * 登陆返回实体信息类
 * Created by huoyan403 on 5/15/2017.
 */
public class UserVO {
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
//    private String realname;
    /**
     * 会员手机号
     **/
    private String phone;
    /**
     * 会员头像
     **/
    private String avatar;
    /**
     * 会员性别
     **/
    private Integer sex;
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
     * 会员是否认证
     **/
    private Integer isAuth;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }
}

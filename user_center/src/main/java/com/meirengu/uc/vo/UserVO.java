package com.meirengu.uc.vo;

import com.meirengu.model.BaseObject;

import java.io.Serializable;
import java.util.Date;

public class UserVO extends BaseObject implements Serializable{

    private String apikey;
    private String token;
    /**
     * 会员id
     **/
    private Integer user_id;
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
     * 会员qq互联id
     **/
    private String qq_openid;
    /**
     * 会员微信
     **/
    private String wx;
    /**
     * 会员微信互联id
     **/
    private String wx_openid;
    /**
     * 会员微博互联id
     **/
    private String sina_openid;
    /**
     * 会员区域id
     **/
    private Integer area_id;
    /**
     * 会员注册来源
     **/

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public String getQq_openid() {
        return qq_openid;
    }

    public void setQq_openid(String qq_openid) {
        this.qq_openid = qq_openid;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    public String getSina_openid() {
        return sina_openid;
    }

    public void setSina_openid(String sina_openid) {
        this.sina_openid = sina_openid;
    }
}
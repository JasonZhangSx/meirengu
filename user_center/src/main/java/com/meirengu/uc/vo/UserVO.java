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
    private int user_id;
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
     * 会员身份证号
     **/
    private String id_card;
    /**
     * 会员密码
     **/
    private String password;
    /**
     * 会员绑定银行卡号
     **/
    private String bank_id_card;
    /**
     * 会员银行预留手机号
     **/
    private String bank_phone;
    /**
     * 会员头像
     **/
    private String avatar;
    /**
     * 会员性别
     **/
    private boolean sex;
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
     * 会员区域id
     **/
    private int area_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBank_id_card() {
        return bank_id_card;
    }

    public void setBank_id_card(String bank_id_card) {
        this.bank_id_card = bank_id_card;
    }

    public String getBank_phone() {
        return bank_phone;
    }

    public void setBank_phone(String bank_phone) {
        this.bank_phone = bank_phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
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

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }
}
package com.meirengu.sms.model;

import java.util.Date;

/**
 * 短信验证码实体类
 *
 * @author Marvin
 * @create 2017-01-12 上午11:50
 */
public class Captcha extends BaseObject {

    /**
     * 验证码索引id
     **/
    private int id;
    /**
     * 手机号
     **/
    private String mobile;
    /**
     * 验证码
     **/
    private int code;
    /**
     * 该验证码的业务类型ID或业务系统内的ID，如登录验证码，服务验证码，订单号等
     **/
    private String uid;
    /**
     * 调用ip地址
     **/
    private String ip;
    /**
     * 验证码生成时间
     **/
    private Date createTime;
    /**
     * 验证码失效时间
     **/
    private Date expireTime;
    /**
     * 是否使用
     **/
    private Boolean isUse;
    /**
     * 使用时间
     **/
    private Date usingTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean getUse() {
        return isUse;
    }

    public void setUse(Boolean use) {
        isUse = use;
    }

    public Date getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Date usingTime) {
        this.usingTime = usingTime;
    }
}

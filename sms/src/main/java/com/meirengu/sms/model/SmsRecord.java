package com.meirengu.sms.model;

import java.util.Date;

/**
 * 短信记录实体类
 *
 * @author Marvin
 * @create 2017-01-16 下午3:04
 */
public class SmsRecord extends BaseObject {

    /**
     * 短信索引id
     */
    int id;
    /**
     * 短信id'
     */
    String sid;
    /**
     * 发送手机号
     */
    String mobile;
    /**
     * 发送时间
     */
    Date sendTime;
    /**
     * 发送内容
     */
    String text;
    /**
     * 响应状态码
     */
    int code;
    /**
     * 响应消息
     */
    String msg;
    /**
     * 发送计费条数
     */
    int count;
    /**
     * 扣费
     */
    double fee;
    /**
     * 系统内id
     */
    String uid;
    /**
     * 发送ip
     */
    String ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
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
}

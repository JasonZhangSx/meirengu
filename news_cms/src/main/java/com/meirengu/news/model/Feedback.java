package com.meirengu.news.model;

import java.util.Date;

/**
 * 意见反馈实体
 * Created by maoruxin on 2017/3/10.
 */
public class Feedback extends BaseEntity{

    /** 意见反馈索引id **/
    private Integer feedbackId;
    /** 反馈内容 **/
    private String feedbackContent;
    /** 提交用户id **/
    private Integer userId;
    /** 提交用户名称(降级 真实姓名--昵称) **/
    private String userName;
    /** 提交用户手机号 **/
    private String userPhone;
    /** 状态：0、未处理；1、已处理 **/
    private Integer status;
    /** 创建时间 **/
    private Date createTime;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package com.meirengu.webview.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/**
 * 常见问题实体
 * Created by huoyan403 on 2016/3/10.
 */
public class Faq extends BaseObject {


    private Integer faqId;
    /** FAQ常见问题索引id **/
    private String faqQuestion;
    /** 常见问题 **/
    private String  faqAnswer;
    /** 常见问题答案 **/
    private Integer classId;
    /** 问题类型 **/
    private String  className;
    /** 问题类型名称 **/
    private Byte  status;
    /** 状态：1、上架；0、下架 **/
    private Date createTime;
    /** 创建时间 **/
    private Date  updateTime;
    /** 更新时间 **/
    private String  operateAccount;
    /** 操作人账号 **/

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
    }

    public String getFaqQuestion() {
        return faqQuestion;
    }

    public void setFaqQuestion(String faqQuestion) {
        this.faqQuestion = faqQuestion;
    }

    public String getFaqAnswer() {
        return faqAnswer;
    }

    public void setFaqAnswer(String faqAnswer) {
        this.faqAnswer = faqAnswer;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount;
    }
}

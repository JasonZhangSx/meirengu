package com.meirengu.news.model;

import java.util.Date;

/**
 * Created by huoyan403 on 3/10/2017.
 */
public class FaqClass {

    private Integer classId;
    /** 常见问题分类索引id **/
    private String className;
    /** 分类名称 **/
    private Byte status;
    /** 状态：1、上架；0、下架 **/
    private Date createTime;
    /** 创建时间 **/
    private Date updateTime;
    /** 更新时间 **/
    private String operateAccount;
    /** 操作人账号 **/

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

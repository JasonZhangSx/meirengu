package com.meirengu.news.model;

import java.util.Date;

/**
 * 公告实体
 * Created by maoruxin on 2017/3/10.
 */
public class Bulletin extends BaseEntity{

    /** 公告索引Id **/
    private Integer bulletinId;
    /** 公告标题 **/
    private String bulletinTitle;
    /** 公告内容 **/
    private String bulletinContent;
    /** 状态：1、上架；0、下架 **/
    private Integer status;
    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date updateTime;
    /** 操作人账号 **/
    private String operateAccount;

    public Integer getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Integer bulletinId) {
        this.bulletinId = bulletinId;
    }

    public String getBulletinTitle() {
        return bulletinTitle;
    }

    public void setBulletinTitle(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }

    public String getBulletinContent() {
        return bulletinContent;
    }

    public void setBulletinContent(String bulletinContent) {
        this.bulletinContent = bulletinContent;
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

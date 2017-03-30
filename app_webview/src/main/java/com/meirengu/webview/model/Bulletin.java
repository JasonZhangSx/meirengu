package com.meirengu.webview.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/**
 * 公告实体
 * Created by maoruxin on 2017/3/10.
 */
public class Bulletin extends BaseObject{

    /** 公告索引Id **/
    private int bulletinId;
    /** 公告标题 **/
    private String bulletinTitle;
    /** 公告内容 **/
    private String bulletinContent;
    /** 状态：1、上架；0、下架 **/
    private int status;
    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date updateTime;
    /** 操作人账号 **/
    private int operateAccount;

    public int getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(int bulletinId) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public int getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(int operateAccount) {
        this.operateAccount = operateAccount;
    }
}

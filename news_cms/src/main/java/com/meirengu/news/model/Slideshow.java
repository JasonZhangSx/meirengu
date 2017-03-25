package com.meirengu.news.model;

import java.util.Date;

public class Slideshow {
    private Integer id;

    private Integer slideshowPosition;

    private String slideshowName;

    private String slideshowImage;

    private String slideshowLink;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String operateAccount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSlideshowPosition() {
        return slideshowPosition;
    }

    public void setSlideshowPosition(Integer slideshowPosition) {
        this.slideshowPosition = slideshowPosition;
    }

    public String getSlideshowName() {
        return slideshowName;
    }

    public void setSlideshowName(String slideshowName) {
        this.slideshowName = slideshowName == null ? null : slideshowName.trim();
    }

    public String getSlideshowImage() {
        return slideshowImage;
    }

    public void setSlideshowImage(String slideshowImage) {
        this.slideshowImage = slideshowImage == null ? null : slideshowImage.trim();
    }

    public String getSlideshowLink() {
        return slideshowLink;
    }

    public void setSlideshowLink(String slideshowLink) {
        this.slideshowLink = slideshowLink == null ? null : slideshowLink.trim();
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
        this.operateAccount = operateAccount == null ? null : operateAccount.trim();
    }

    @Override
    public String toString() {
        return "Slideshow{" +
                "id=" + id +
                ", slideshowPosition=" + slideshowPosition +
                ", slideshowName='" + slideshowName + '\'' +
                ", slideshowImage='" + slideshowImage + '\'' +
                ", slideshowLink='" + slideshowLink + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operateAccount='" + operateAccount + '\'' +
                '}';
    }
}
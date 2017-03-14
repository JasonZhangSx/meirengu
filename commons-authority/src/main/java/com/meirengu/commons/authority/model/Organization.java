package com.meirengu.commons.authority.model;

import java.util.Date;

public class Organization {
    /**
     * 组织机构索引id
     */
    private Integer organizationId;
    /**
     * 组织机构名称
     */
    private String organizationName;
    /**
     * 组织机构父id
     */
    private Integer organizationParentId;
    /**
     * 状态：1、启用；0、禁用
     */
    private Boolean organizationStatus;
    /**
     * 排序：数字越小权重越大
     */
    private Boolean organizationSort;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 操作人账号
     */
    private String operateAccount;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public Integer getOrganizationParentId() {
        return organizationParentId;
    }

    public void setOrganizationParentId(Integer organizationParentId) {
        this.organizationParentId = organizationParentId;
    }

    public Boolean getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(Boolean organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public Boolean getOrganizationSort() {
        return organizationSort;
    }

    public void setOrganizationSort(Boolean organizationSort) {
        this.organizationSort = organizationSort;
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
        return "Organization{" +
                "organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                ", organizationParentId=" + organizationParentId +
                ", organizationStatus=" + organizationStatus +
                ", organizationSort=" + organizationSort +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operateAccount='" + operateAccount + '\'' +
                '}';
    }
}
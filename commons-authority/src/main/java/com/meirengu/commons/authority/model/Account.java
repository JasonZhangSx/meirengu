package com.meirengu.commons.authority.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Account implements Serializable {
    /**
     * 账户主键
     */
    private Integer id;
    /**
     * 账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String userPhone;
    /**
     * 邮箱地址
     */
    private String userEmail;
    /**
     * 所属组织机构id
     */
    private Integer organizationId;
    /**
     * 状态：1、启用；0、禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 到期时间
     */
    private Date expireTime;
    /**
     * 操作人账号
     */
    private String operateAccount;
    /**
     * 所属组织机构
     */
    private Organization organization;
    /**
     * 角色集合
     */
    private List<Role> roleList;
    /**
     * 权限集合
     */
    private List<Permission> permissionsList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(List<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", organizationId=" + organizationId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", operateAccount='" + operateAccount + '\'' +
                ", organization=" + organization +
                ", roleList=" + roleList +
                ", permissionsList=" + permissionsList +
                '}';
    }
}
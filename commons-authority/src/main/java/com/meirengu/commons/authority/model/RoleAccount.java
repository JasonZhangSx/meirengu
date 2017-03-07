package com.meirengu.commons.authority.model;

public class RoleAccount {
    /**
     * 角色用户关联id
     */
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 用户id
     */
    private Long accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "RoleAccount{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", accountId=" + accountId +
                '}';
    }
}
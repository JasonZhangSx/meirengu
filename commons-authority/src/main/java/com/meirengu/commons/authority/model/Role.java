package com.meirengu.commons.authority.model;

import java.util.List;


public class Role extends BaseObject {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String value;
    private String siteIds;
    private List<User> userList;
    private List<Permission> permissionList;
    private List<String> pList; //权限字符串list

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userList
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * @return the permissionList
     */
    public List<Permission> getPermissionList() {
        return permissionList;
    }

    /**
     * @param permissionList the permissionList to set
     */
    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    /**
     * @return the pList
     */
    public List<String> getpList() {
        return pList;
    }

    /**
     * @param pList the pList to set
     */
    public void setpList(List<String> pList) {
        this.pList = pList;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the siteIds
     */
    public String getSiteIds() {
        return siteIds;
    }

    /**
     * @param siteIds the siteIds to set
     */
    public void setSiteIds(String siteIds) {
        this.siteIds = siteIds;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Role(Integer id) {
        super();
        this.id = id;
    }

    public Role() {
        super();
    }

}

package com.meirengu.commons.authority.model;

import java.util.List;


public class Permission extends BaseObject {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String type;
    private String value;
    private List<Role> roleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Permission(Integer id) {
        super();
        this.id = id;
    }

    public Permission() {
        super();
    }

}

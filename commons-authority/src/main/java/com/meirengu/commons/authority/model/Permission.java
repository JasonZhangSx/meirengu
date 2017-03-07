package com.meirengu.commons.authority.model;

import java.util.List;

public class Permission {
    /**
     * 权限id
     */
    private Long id;
    /**
     *父权限id
     */
    private Integer parentId;
    /**
     * 权限名称
     */
    private String name;
    /**
     *权限值
     */
    private String value;
    /**
     * 权限描述
     */
    private String description;
    /**
     * 权限集合
     */
    private List<Permission> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public List<Permission> getList() {
        return list;
    }

    public void setList(List<Permission> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", list=" + list +
                '}';
    }
}
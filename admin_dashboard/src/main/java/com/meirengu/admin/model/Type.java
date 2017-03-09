package com.meirengu.admin.model;

import java.io.Serializable;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/11 20:05.
 * 商品类型Model
 */
public class Type implements Serializable {
    private Integer typeId;

    private String typeName;

    private Integer typeSort;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", typeSort=" + typeSort +
                '}';
    }
}

package com.meirengu.medical.model;

public class Attribute {
    private Integer attrId;

    private String attrName;

    private Integer typeId;

    private Boolean attrShow;

    private Boolean attrSort;

    private String attrValue;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getAttrShow() {
        return attrShow;
    }

    public void setAttrShow(Boolean attrShow) {
        this.attrShow = attrShow;
    }

    public Boolean getAttrSort() {
        return attrSort;
    }

    public void setAttrSort(Boolean attrSort) {
        this.attrSort = attrSort;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }
}
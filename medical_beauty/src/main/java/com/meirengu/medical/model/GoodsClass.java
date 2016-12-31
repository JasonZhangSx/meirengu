package com.meirengu.medical.model;

public class GoodsClass {
    private Integer gcId;

    private String gcName;

    private Integer typeId;

    private String typeName;

    private Integer storeId;

    private Integer gcParentId;

    private Boolean gcSort;

    private Boolean gcShow;

    private String gcTitle;

    private String gcKeywords;

    private String gcDescription;

    public Integer getGcId() {
        return gcId;
    }

    public void setGcId(Integer gcId) {
        this.gcId = gcId;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName == null ? null : gcName.trim();
    }

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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getGcParentId() {
        return gcParentId;
    }

    public void setGcParentId(Integer gcParentId) {
        this.gcParentId = gcParentId;
    }

    public Boolean getGcSort() {
        return gcSort;
    }

    public void setGcSort(Boolean gcSort) {
        this.gcSort = gcSort;
    }

    public Boolean getGcShow() {
        return gcShow;
    }

    public void setGcShow(Boolean gcShow) {
        this.gcShow = gcShow;
    }

    public String getGcTitle() {
        return gcTitle;
    }

    public void setGcTitle(String gcTitle) {
        this.gcTitle = gcTitle == null ? null : gcTitle.trim();
    }

    public String getGcKeywords() {
        return gcKeywords;
    }

    public void setGcKeywords(String gcKeywords) {
        this.gcKeywords = gcKeywords == null ? null : gcKeywords.trim();
    }

    public String getGcDescription() {
        return gcDescription;
    }

    public void setGcDescription(String gcDescription) {
        this.gcDescription = gcDescription == null ? null : gcDescription.trim();
    }
}